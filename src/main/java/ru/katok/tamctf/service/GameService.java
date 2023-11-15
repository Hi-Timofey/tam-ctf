package ru.katok.tamctf.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.katok.tamctf.config.PlatformConfig;
import ru.katok.tamctf.domain.dto.TaskDto;
import ru.katok.tamctf.domain.entity.Task;
import ru.katok.tamctf.repository.TaskRepository;
import ru.katok.tamctf.service.interfaces.IGameService;

import java.util.List;

@Service
@AllArgsConstructor
public class GameService implements IGameService {

    private final TaskRepository taskRepository;
    private final PlatformConfig platformConfig = new PlatformConfig();


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
        platformConfig.setCtfTitle(flagWrapper);
    }

    @Override
    public List<TaskDto> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return ;
    }

}
