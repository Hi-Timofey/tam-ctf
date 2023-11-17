package ru.katok.tamctf.service.interfaces;

import ru.katok.tamctf.domain.dto.CategoryDto;
import ru.katok.tamctf.domain.dto.TaskDto;
import ru.katok.tamctf.domain.entity.Task;
import ru.katok.tamctf.domain.error.TaskExistsException;

import java.util.List;
import java.util.Optional;

public interface ITaskService {
    List<Task> getAll();
    Task createNewTask(TaskDto newTask, String categoryName) throws TaskExistsException;

    Optional<Task> findTaskByName(String name);

    void deleteTask(Task task);

    void changeTaskName(Task task, String name);

    void changeTaskDescription(Task task, String description);

    void changeTaskFlag(Task task, String flag);
}
