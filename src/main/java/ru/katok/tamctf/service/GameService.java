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
import ru.katok.tamctf.service.errors.FlagUnpackError;
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


    public PlatformConfig retrieveGameConfig() {
        return platformConfig;
    }

    @Secured("ROLE_ADMIN")
    public void setPlatformConfig(PlatformConfig platformConfig) {
        this.platformConfig = platformConfig;
    }

    @Deprecated(forRemoval = false)
    public List<PublicTaskDto> getAllTasks() {

        if (!isGameStarted()) {
            return List.of();
        }

        List<PublicTaskDto> publicTaskDto = taskRepository.findAll().stream().map(MappingUtil::mapToPublicTaskDto).toList();
        for (PublicTaskDto task : publicTaskDto) {
            task.setSolves(submissionRepository.countAllByIsSuccessfulIsTrueAndTaskId(task.getId()));
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
    public boolean solveTask(String flag,Long taskId, String username) throws GameError {


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



        if (submissionRepository.findSolvesByTeam(task.getName(), userTeam.getId())  ) {
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
        } catch (Exception e){
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
