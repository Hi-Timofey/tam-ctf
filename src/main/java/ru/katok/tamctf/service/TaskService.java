package ru.katok.tamctf.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import ru.katok.tamctf.domain.dto.TaskDto;
import ru.katok.tamctf.domain.entity.Category;
import ru.katok.tamctf.domain.entity.Task;
import ru.katok.tamctf.domain.error.CategoryNotFoundException;
import ru.katok.tamctf.domain.error.TaskExistsException;
import ru.katok.tamctf.domain.error.TaskNotFoundException;
import ru.katok.tamctf.domain.util.MappingUtil;
import ru.katok.tamctf.repository.CategoryRepository;
import ru.katok.tamctf.repository.TaskRepository;
import ru.katok.tamctf.service.interfaces.ITaskService;
import ru.katok.tamctf.service.TelegramService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service("taskService")
public class TaskService implements ITaskService {
    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final TelegramService telegramService;
    @Override
    public List<TaskDto> getAll() {
        return taskRepository.findAll().stream()
                .map(MappingUtil::mapToTaskDto).toList();
    }

    @Override
    @Secured("ROLE_ADMIN")
    public TaskDto createNewTask(TaskDto newTask) throws TaskExistsException {
        Optional<Category> optionalCategory = categoryRepository.findByName(newTask.getCategory());
        Category category;
        if (optionalCategory.isPresent()) {
            category = optionalCategory.get();
        } else {
            throw new CategoryNotFoundException("There is no such category as " + newTask.getCategory());
        }
        if (taskRepository.existsByName(newTask.getName())) {
            throw new TaskExistsException("A task with that name already exists.");
        }
        Task task = Task.builder()
                .name(newTask.getName())
                .description(newTask.getDescription())
                .adminDescription(newTask.getAdminDescription())
                .flag(newTask.getFlag())
                .taskDifficulty(newTask.getTaskDifficulty())
                .author(newTask.getAuthor())
                .scoreInitial(newTask.getScoreInitial())
                .scoreDelay(newTask.getScoreDelay())
                .scoreMinimum(newTask.getScoreMinimum())
                .active(newTask.isActive())
                .category(category)
                .build();

        telegramService.newTaskTelegramNotification(task);
        return MappingUtil.mapToTaskDto(taskRepository.save(task));
    }

    @Override
    public Optional<TaskDto> findTaskByName(String name) {
        return taskRepository.findByName(name).map(MappingUtil::mapToTaskDto);
    }

    public TaskDto getById(Long id) {
        return MappingUtil.mapToTaskDto(this.taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("no such task with id: " + id)));
    }

    public TaskDto save(Task newTask) {
        return MappingUtil.mapToTaskDto(this.taskRepository.save(newTask));
    }
    //    @Override
//    public void changeTaskName(Task task, String name){
//        task.setName(name);
//        taskRepository.save(task);
//    }
//    @Override
//    public void changeTaskDescription(Task task, String description){
//        task.setDescription(description);
//        taskRepository.save(task);
//    }
//    @Override
//    public void changeTaskFlag(Task task, String flag){
//        task.setFlag(flag);
//        taskRepository.save(task);
//    }
    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("no such task with id: " + id));
        taskRepository.delete(task);
    }
}

