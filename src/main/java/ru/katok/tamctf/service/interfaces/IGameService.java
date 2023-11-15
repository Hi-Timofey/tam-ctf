package ru.katok.tamctf.service.interfaces;

import ru.katok.tamctf.config.PlatformConfig;
import ru.katok.tamctf.domain.dto.TaskDto;

import java.util.List;

public interface IGameService {
    PlatformConfig retriveGameConfig();

    void setCtfTitle(String ctfTitle);

    void setFlagWrapper(String flagWrapper);

    List<TaskDto> getAllTasks();
}
