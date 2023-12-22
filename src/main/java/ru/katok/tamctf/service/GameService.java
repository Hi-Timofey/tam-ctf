package ru.katok.tamctf.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.katok.tamctf.config.PlatformConfig;
import ru.katok.tamctf.domain.dto.CategoryDto;
import ru.katok.tamctf.domain.entity.*;
import ru.katok.tamctf.domain.error.UserNotFoundException;
import ru.katok.tamctf.domain.util.MappingUtil;
import ru.katok.tamctf.repository.*;
import ru.katok.tamctf.service.dto.PublicTaskDto;
import ru.katok.tamctf.service.dto.Score;
import ru.katok.tamctf.service.errors.FlagUnpackError;
import ru.katok.tamctf.service.errors.GameError;
import ru.katok.tamctf.service.interfaces.IGameService;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class GameService implements IGameService {
    private final CategoryRepository categoryRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final SubmissionRepository submissionRepository;

    private PlatformConfig platformConfig;
    private ObjectMapper objectMapper;


    private String unpackFlagInFormat(String flag) {
        String[] splitted = flag.split("\\{");
        if (splitted.length != 2) {
            throw new FlagUnpackError("Invalid flag format: len(unpacked) != 2");
        }
        if (!splitted[1].endsWith("}")) {
            throw new FlagUnpackError("Invalid flag format: flag doesn't end with }");
        }
        if (!splitted[0].equalsIgnoreCase(platformConfig.getFlagWrapper())) {
            throw new FlagUnpackError("Invalid flag format: flag doesn't start with flagwrapper");
        }
        return splitted[1].substring(0, splitted[1].length() - 1);
    }

    protected boolean isGameStarted() {
        return LocalDateTime.now().isAfter(platformConfig.getGameStartTime());
    }

    protected boolean isFreeze() {
        return LocalDateTime.now().isAfter(platformConfig.getFreezeStartTime()) &&
                LocalDateTime.now().isBefore(platformConfig.getGameEndTime());
    }

    private int countTaskSolves(Long taskId) {
        return submissionRepository.countAllByIsSuccessfulIsTrueAndTaskId(taskId);
    }


    private int computeTaskScore(Task task, int solves) {
        if (solves != 0) {
            solves--;
        }
        int scoreInitial = task.getScoreInitial();
        int scoreDecay = task.getScoreDelay();
        int scoreMinimum = task.getScoreMinimum();

        double value = (((scoreMinimum - scoreInitial) / (scoreDecay * scoreDecay)) * (solves * solves)) + scoreInitial;

        int result = (int) Math.ceil(value);
        if (result < scoreMinimum) result = scoreMinimum;
        return result;
    }

    public PlatformConfig retrieveGameConfig() {
        return platformConfig;
    }

    @Secured("ROLE_ADMIN")
    public void setPlatformConfig(PlatformConfig platformConfig) {
        this.platformConfig = platformConfig;
    }


    @Secured("ROLE_USER")
    //TODO: Needs refactor
    public List<Score> getScoreboard() {
        if (!isGameStarted()) {
            log.info("User tried to get task list while game isn't started");
            return List.of();
        }
        List<Team> teams = teamRepository.findAll();
        List<Score> scores = new ArrayList<>();
        for (Team team : teams) {
            Score s = Score.builder()
                    .teamName(team.getName())
                    .teamType(team.getTeamType())
                    .score(0)
                    .build();
            scores.add(s);
        }

        List<Task> tasks = taskRepository.findAll();
        Map<Task, Integer> scoreMap = new HashMap<>();
        for (Task task : tasks) {
            int solves = countTaskSolves(task.getId());
            int score = computeTaskScore(task, solves);
            scoreMap.put(task, score);
        }

        for (Score s : scores) {
            s.setScore(0);
            for (Task task : tasks) {
                for (Submission sub : submissionRepository.findAllSuccsessfulByTask(task)) {
                    if (sub.getUser().getTeam().getName().equals(s.getTeamName())) {
                        s.setScore(s.getScore() + scoreMap.get(task));
                    }
                }
            }
        }
        return scores;
    }

    public List<PublicTaskDto> getAllTasks() {

        if (!isGameStarted()) {
            log.info("User tried to get task list while game isn't started");
            return List.of();
        }

        List<Task> tasks = taskRepository.findAll();
        List<PublicTaskDto> publicTaskDto = tasks.stream().map(MappingUtil::mapToPublicTaskDto).toList();
        for (int i = 0; i < tasks.size(); i++) {
            PublicTaskDto task = publicTaskDto.get(i);

            int solves = countTaskSolves(task.getId());

            task.setSolves(solves);
            task.setScore(computeTaskScore(tasks.get(i), solves));
        }
        return publicTaskDto;
    }

    public CategoryDto createNewCategory(CategoryDto newCategory) {
        Category category = Category.builder()
                .name(newCategory.getName())
                .build();
        categoryRepository.save(category);
        return MappingUtil.mapToCategoryDto(category);
    }

    @Secured("ROLE_USER")
    public boolean submitFlag(String flag, Long taskId, String username) throws GameError {


        if (!isGameStarted()) {
            log.info("User %s tried to submit flag while game hasn't started".formatted(username));
            return false;
        }

        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isEmpty()) throw new UserNotFoundException("There is no account with that nickname: " + username);
        UserEntity userEntity = user.get();

        Team userTeam = userEntity.getTeam();
        if (userTeam == null) {
            log.info("User %s tried to submit flag without team".formatted(username));
            return false;
        }


        Optional<Task> taskOptional = taskRepository.findById(taskId);

        if (taskOptional.isEmpty()) {
            log.info("User %s tried to submit non-existent or inactive task".formatted(username));
            return false;
        }
        Task task = taskOptional.get();


        if (submissionRepository.findSolvesByTeam(task.getName(), userTeam.getId())) {
            log.info("User %s tried to submit already solved task %d".formatted(username, task.getId()));
            return false;
        }
        Submission submission = Submission.builder()
                .isSuccessful(false) // Make false at start so if correct answer - true
                .flag(flag)
                .solverIp(null)
                .task(task)
                .user(userEntity)
                .team((userEntity.getTeam()))
                .build();

        String extractedFlag;
        try {
            extractedFlag = unpackFlagInFormat(flag);
            submission.setSuccessful(task.getFlag().equals(extractedFlag));
        } catch (FlagUnpackError e) {
            log.info("User %s tried to submit invalid flag: %s".formatted(username, e.getMessage()));
            submission.setSuccessful(false);
        } catch (Exception e) {
            log.error("User %s tried to submit flag, but something went REALLY WRONG: %s".formatted(username, e.getMessage()));
            submission.setSuccessful(false);
        }

        submissionRepository.save(submission);
        return submission.isSuccessful();
    }

    @Transactional
    public void deleteCategory(String name) {
        categoryRepository.deleteByName(name);
    }

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(MappingUtil::mapToCategoryDto).toList();
    }

    //TODO::Лол, метод сейв не подходит для редактирования категории,
    // CrudeRep думает что мы создаем наый объек, надо фиксить
    // @DrunkardKirA
    public Category applyPatchToCategory(JsonPatch patch, Category category) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(category, JsonNode.class));
        return objectMapper.treeToValue(patched, Category.class);
    }

    public CategoryDto editCategoryByName(String name, JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        Optional<Category> optionalCategory = categoryRepository.findByName(name);
        Category category = optionalCategory.get();
        Category patchedCategory = applyPatchToCategory(patch, category);
        categoryRepository.save(patchedCategory);
        return MappingUtil.mapToCategoryDto(patchedCategory);
    }

    public String dumpScores() throws IOException {
        List<Score> scores = new ArrayList<>();
        scores = getScoreboard();
        String fileName = "scoreboard.json";
        Path filePath = Paths.get(".", fileName);
        Path exportedFilePath;
        Gson gson = new Gson();
        String customerInJson = gson.toJson(scores);
        return customerInJson;
    }


    private int rowsCount(Connection con, String request) throws SQLException {

        Statement st = con.createStatement();
        ResultSet res = st.executeQuery(request);
        res.next();
        return res.getInt("NumberOfRows");
    }

    private int colsCount(Connection con, String request) throws SQLException {

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(request);
        ResultSetMetaData rsmd = rs.getMetaData();
        int column_count = rsmd.getColumnCount();
        return column_count;
    }

    @Deprecated
    public String backUp() throws SQLException, IOException {

        String url = "jdbc:postgresql://localhost:5432/ctf";
        String user = "ctf";
        String password = "ctf";
        List<String> tables = new ArrayList<String>();
        try (Connection connect = DriverManager.getConnection(url, user, password)) {
            try {

                DatabaseMetaData dataBaseMetaData = connect.getMetaData();
                String[] types = {"TABLE"};
                ResultSet resultSet = dataBaseMetaData.getTables(null, null, "%", types);
                while (resultSet.next()) {
                    tables.add(resultSet.getString(3));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            Connection con = DriverManager.getConnection(url, user, password);
            String tempSelect = "SELECT * FROM ";
            String tempCount = "SELECT COUNT(1) as NumberOfRows FROM ";
            String sqlRequest;
            String countRequest;

            for (String s : tables) {
                List<String> resultSetArray = new ArrayList<>();
                sqlRequest = tempSelect.concat(s);
                countRequest = tempCount.concat(s);
                PreparedStatement pst = con.prepareStatement(sqlRequest);
                ResultSet rs = pst.executeQuery();
                int countOfRows = rowsCount(con, countRequest);
                int countOfCols = colsCount(con, sqlRequest);
                while (rs.next()) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 1; i <= countOfCols + 1; i++) {
                        /*                   if(rs.getObject(i) != null){*/
                        sb.append(String.format(String.valueOf(rs.getString(i))));
                        if (i >= countOfCols) {
                            sb.append(";");
                            resultSetArray.add(sb.toString());
                            break;
                        }
                        sb.append(" ");
                    }

                }
                String tempPath = ".";
                String filePath = tempPath.concat(s).concat(".csv");
                /* BufferedReader csvReader = new BufferedReader(new FileReader("C:\\Users\\Documents\\MyFile.csv"));*/
                FileWriter fileWriter = new FileWriter(filePath);
                PrintWriter printWriter = new PrintWriter(fileWriter);
                printWriter.printf(resultSetArray.toString());

                printWriter.close();
            }
        }
        return tables.toString();
    }
}
