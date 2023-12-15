package ru.katok.tamctf.service;

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
            log.info("User tried to get task list whil game isn't started");
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
            log.info("User tried to get task list whil game isn't started");
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

}
