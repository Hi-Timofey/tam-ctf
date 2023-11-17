package ru.katok.tamctf.api.rest.admin;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.katok.tamctf.api.util.GenericResponse;
import ru.katok.tamctf.domain.entity.Task;
import ru.katok.tamctf.service.TaskService;

import java.util.List;
@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class TaskAdminController {
    private final TaskService taskService;

    @ResponseBody
    @GetMapping(path = "tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Task> getAllTasks() {
        return taskService.getAll();
    }

    @PostMapping(path = "tasks", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Task newTask(@RequestBody Task newTask) {
        return this.taskService.save(newTask);
    }

    @GetMapping(path = "tasks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Task getTaskById(@PathVariable Long id) {
        return this.taskService.getById(id);
    }

    @DeleteMapping(path = "tasks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse deleteTask(@PathVariable Long id) {
        this.taskService.deleteTask(this.taskService.getById(id));
        return new GenericResponse(true, "ok");
    }
}
