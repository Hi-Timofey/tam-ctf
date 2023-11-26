package ru.katok.tamctf.api.rest;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.katok.tamctf.api.util.GenericResponse;
import ru.katok.tamctf.config.PlatformConfig;
import ru.katok.tamctf.domain.dto.TaskDto;
import ru.katok.tamctf.service.GameService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class GameController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final GameService gameService;


    @GetMapping(path = "/config", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse<PlatformConfig> getConfig() {
        return new GenericResponse<>(true, "ok", gameService.retrieveGameConfig());
    }

    @GetMapping(path = "/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse<List<TaskDto>> getActiveTasks() {
        return new GenericResponse<>(true, "ok", gameService.getAllTasks());
    }

    @GetMapping(path = "/scoreboard", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse getScoreboard() {
        return new GenericResponse();
    }

    @PostMapping(path = "/solve", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse solveTask(@AuthenticationPrincipal UserDetails user) {
        log.debug("Got solve on logger!");
        return new GenericResponse();
    }
}
