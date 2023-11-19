package ru.katok.tamctf.service;

import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import ru.katok.tamctf.config.PlatformConfig;
import ru.katok.tamctf.domain.dto.TaskDto;
import ru.katok.tamctf.domain.entity.Task;
import ru.katok.tamctf.domain.util.MappingUtil;
import ru.katok.tamctf.repository.TaskRepository;
import ru.katok.tamctf.service.interfaces.IGameService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class GameService implements IGameService {

    private final TaskRepository taskRepository;
    private PlatformConfig platformConfig;


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


    @Override
    public PlatformConfig retriveGameConfig() {
        return platformConfig;
    }

    @Secured("ROLE_ADMIN")
    public void setPlatformConfig(PlatformConfig platformConfig) {
        this.platformConfig =  platformConfig;
    }

    @Override
    public List<TaskDto> getAllTasks() {

        if (!isGameStarted()){
            return List.of();
        }

        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(MappingUtil::mapToTaskDto).toList();
    }

}
