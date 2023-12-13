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
import ru.katok.tamctf.service.errors.GameError;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

@Service
@AllArgsConstructor
@Slf4j
public class GameService {
    private final CategoryRepository categoryRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final SubmissionRepository submissionRepository;

    private PlatformConfig platformConfig;


    private String extractedFlag(String flag){
        String[] splitted =  flag.split("\\{");
        if (splitted.length != 2 ) {
            throw new RuntimeException("Invalid flag format: len(unpacked) != 2");
        }
        if (!splitted[1].endsWith("}")   ) {
            throw new RuntimeException("Invalid flag format: flag doesn't end with }");
        }
        if (!splitted[0].toLowerCase().equals(platformConfig.getFlagWrapper().toLowerCase())){
            throw new RuntimeException("Invalid flag format: flag doesn't start with flagwrapper");
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


    public PlatformConfig retrieveGameConfig() {
        return platformConfig;
    }

    @Secured("ROLE_ADMIN")
    public void setPlatformConfig(PlatformConfig platformConfig) {
        this.platformConfig =  platformConfig;
    }

   /* @Deprecated(forRemoval = false)
    public List<TaskDto> getAllTasks() {

        if (!isGameStarted()){
            return List.of();
        }

        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(MappingUtil::mapToTaskDto).toList();
    }*/
    @Deprecated(forRemoval = false)
    public List<PublicTaskDto> getAllTasks() {

        if (!isGameStarted()){
            return List.of();
        }


        List<Task> tasks = taskRepository.findAll();
        Vector<Integer> v = new Vector<Integer>(0);
        for (Task t : tasks) {
            v.addElement(submissionRepository.countAllByIsSuccessfulIsTrueAndTaskId(t.getId()));
        }
        List<PublicTaskDto> publicTaskDto = tasks.stream().map(MappingUtil::mapToPublicTaskDto).toList();
        for (PublicTaskDto t : publicTaskDto) {
            t.setSolves(v.firstElement());
            v.remove(0);
        }
    return publicTaskDto;
    }
    public CategoryDto createNewCategory(CategoryDto newCategory){
        Category category = Category.builder()
                .name(newCategory.getName())
                .build();
        categoryRepository.save(category);
        return MappingUtil.mapToCategoryDto(category);
    }

    @Secured("ROLE_USER")
    public boolean solveTask(String flag,String username) throws GameError{

        boolean successful = true;

       //TODO: refactoring ->
        if (!isGameStarted()) {
            log.info("User %s tried to submit flag while game hasn't started".formatted(username));
            successful = false;
        }


        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isEmpty()) throw new UserNotFoundException("There is no account with that nickname: " + username);
        UserEntity userEntity = user.get();
        Team userTeam = userEntity.getTeam();
        if (userTeam == null && successful) {
            log.info("User %s tried to submit flag without team".formatted(username));
            successful = false;
        }

        String extractedFlag = null;
        try {
            extractedFlag = extractedFlag(flag);
        } catch (RuntimeException e) {
            log.info("User %s tried to submit invalid flag: %s".formatted(username, e.getMessage()));
            successful = false;
        }

        Optional<Task> taskOptional = taskRepository.findByActiveTrueAndFlagIs(extractedFlag);

        if (taskOptional.isEmpty() && successful) {
            log.info("User %s tried to submit non-existent or inactive task".formatted(username));
            successful = false;
        }
        Task task = null;
        if(successful) {
            task = taskOptional.get(); //TODO Do Something here!!!
        }
        if (submissionRepository.findSolvesByTeam(task.getName(), userTeam.getId()) && successful){
            log.info("User %s tried to submit already solved task %s".formatted(username));
            successful = false;
        }
        if(!task.getFlag().equals(extractedFlag(flag)) && successful){
            log.info("User %s tried to submit wrong flag".formatted(username));
            successful = false;
        }
        Submission submission = Submission.builder()
                .isSuccessful(successful)//TODO: <-refactoring
                .flag(flag)
                .solverIp(null) //TODO: NEED TO CHECK USER IP BEFORE CREATING SUBMISSIONS
                .task(task)
                .user(userEntity)
                .team((userEntity.getTeam()))
                .build();
        submissionRepository.save(submission);
        return successful;
    }

    @Transactional
    public void deleteCategory(String name){
        categoryRepository.deleteByName(name);
    }

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(MappingUtil::mapToCategoryDto).toList();
    }

}
