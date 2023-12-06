package ru.katok.tamctf.api.rest.admin;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.katok.tamctf.api.util.GenericResponse;
import ru.katok.tamctf.domain.dto.TaskDto;
import ru.katok.tamctf.service.TaskService;

import java.util.List;
@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class TaskAdminController {

    private final TaskService taskService;

    @ResponseBody
    @GetMapping(path = "tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse<List<TaskDto>> getAllTasks() {
        return new GenericResponse<>(true, "ok", taskService.getAll());
    }
    @PostMapping(path = "/tasks", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse<TaskDto> createTask (@RequestBody TaskDto newTask) {
        TaskDto task = taskService.createNewTask(newTask);
        return new GenericResponse<>(true, "ok", task);
    }

    @GetMapping(path = "tasks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse<TaskDto> getTaskById(@PathVariable Long id) {
        return new GenericResponse<>( true, "ok", taskService.getById(id));
    }

    @DeleteMapping(path = "tasks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse deleteTask(@PathVariable Long id) {
        this.taskService.deleteTask(id);
        return new GenericResponse<>(true, "ok");
    }
}
