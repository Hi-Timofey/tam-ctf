package ru.katok.tamctf.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.katok.tamctf.domain.dto.TaskDto;
import ru.katok.tamctf.domain.entity.Task;
import ru.katok.tamctf.domain.dto.CategoryDto;
import ru.katok.tamctf.domain.entity.Category;
import ru.katok.tamctf.domain.error.*;
import ru.katok.tamctf.repository.TaskRepository;
import ru.katok.tamctf.repository.CategoryRepository;
import ru.katok.tamctf.service.interfaces.ITaskService;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service("taskService")
public class TaskService implements ITaskService {
    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task createNewTask(TaskDto newTask, String categoryName) throws TaskExistsException {
        Optional<Category> optionalCategory = categoryRepository.findByName(categoryName);
        if (optionalCategory.isPresent()){
            Category category = optionalCategory.get();
        } else {
            throw new CategoryNotFoundException("There is no such category as " + categoryName);
        }
        if (taskRepository.existsByName(newTask.getName())) {
            throw new TeamAlreadyExistException("A task with that name already exists.");
        }
        Task task = Task.builder().name(newTask.getName()).description(newTask.getDescription()).flag(newTask.getFlag()).build();
        return taskRepository.save(task);
    }
    @Override
    public Optional<Task> findTaskByName(String name) {
        return taskRepository.findByName(name);
    }

    public Task getById(final Long id) {
        return this.taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("no such task with id: " + Long.toString(id)));
    }
    public Task save(Task newTask) {
        return this.taskRepository.save(newTask);
    }
    @Override
    public void changeTaskName(Task task, String name){
        task.setName(name);
        taskRepository.save(task);
    }
    @Override
    public void changeTaskDescription(Task task, String description){
        task.setDescription(description);
        taskRepository.save(task);
    }
    @Override
    public void changeTaskFlag(Task task, String flag){
        task.setFlag(flag);
        taskRepository.save(task);
    }
    @Override
    public void deleteTask(final Task task) {
        this.taskRepository.delete(task);
    }


}
