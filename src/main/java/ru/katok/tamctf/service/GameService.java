package ru.katok.tamctf.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.katok.tamctf.config.PlatformConfig;
import ru.katok.tamctf.domain.dto.CategoryDto;
import ru.katok.tamctf.domain.dto.TaskDto;
import ru.katok.tamctf.domain.entity.*;
import ru.katok.tamctf.domain.error.UserNotFoundException;
import ru.katok.tamctf.domain.util.MappingUtil;
import ru.katok.tamctf.repository.CategoryRepository;
import ru.katok.tamctf.repository.SubmissionRepository;
import ru.katok.tamctf.repository.TaskRepository;
import ru.katok.tamctf.repository.UserRepository;
import ru.katok.tamctf.service.errors.GameError;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.*;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class GameService {
    private final CategoryRepository categoryRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
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
        if (splitted[0].toLowerCase() != platformConfig.getFlagWrapper().toLowerCase()){
            throw new RuntimeException("Invalid flag format: flag doesn't start with flagwrapper");
        }
        return splitted[1].substring(0, splitted[1].length() - 1);
    }

    protected boolean isGameEnded(){
        return LocalDateTime.now().isAfter(platformConfig.getGameEndTime());
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

    @Deprecated(forRemoval = false)
    public List<TaskDto> getAllTasks() {

        if (!isGameStarted()){
            return List.of();
        }

        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(MappingUtil::mapToTaskDto).toList();
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
        if (!isGameStarted()){
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

        String extractedFlag;
        try {
            extractedFlag = extractedFlag(flag);
        } catch (RuntimeException e) {
            log.info("User %s tried to submit invalid flag: %s".formatted(username, e.getMessage()));
            return false;
        }

        Optional<Task> taskOptional = taskRepository.findByActiveTrueAndFlagIs(extractedFlag);

        if (taskOptional.isEmpty()) {
            log.info("User %s tried to submit non-existent or inactive task".formatted(username));
            return false;
        }
        Task task = taskOptional.get();

        if (submissionRepository.findSolvesByTeam(task.getName(), userTeam.getId())  ){
            log.info("User %s tried to submit already solved task %s".formatted(username));
            return false;
        }

        return true;
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
