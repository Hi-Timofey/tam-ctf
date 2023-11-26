package ru.katok.tamctf.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.katok.tamctf.config.PlatformConfig;
import ru.katok.tamctf.domain.dto.TaskDto;
import ru.katok.tamctf.domain.entity.Task;
import ru.katok.tamctf.domain.util.MappingUtil;
import ru.katok.tamctf.repository.TaskRepository;
import ru.katok.tamctf.service.interfaces.IGameService;

import java.util.List;

@Service
public class GameService implements IGameService {

    private final TaskRepository taskRepository;
    private PlatformConfig platformConfig;

    GameService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        this.platformConfig =  new PlatformConfig();
    }


    @Override
    public PlatformConfig retriveGameConfig() {
        return platformConfig;
    }

    @Override
    public void setCtfTitle(String ctfTitle) {
        platformConfig.setCtfTitle(ctfTitle);
    }

    @Override
    public void setFlagWrapper(String flagWrapper) {
        platformConfig.setFlagWrapper(flagWrapper);
    }

    @Override
    public List<TaskDto> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        ModelMapper mm = new ModelMapper();
        return tasks.stream()
                .map(MappingUtil::mapToTaskDto).toList();
    }

}
