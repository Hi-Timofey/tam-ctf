package ru.katok.tamctf.service.interfaces;

import ru.katok.tamctf.domain.dto.CategoryDto;
import ru.katok.tamctf.domain.dto.TaskDto;
import ru.katok.tamctf.domain.entity.Task;
import ru.katok.tamctf.domain.error.TaskExistsException;

import java.util.List;
import java.util.Optional;

public interface ITaskService {
    List<TaskDto> getAll();
    TaskDto createNewTask(TaskDto newTask, String categoryName) throws TaskExistsException;

    Optional<TaskDto> findTaskByName(String name);

    void deleteTask(Long id);
}
