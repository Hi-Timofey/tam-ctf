package ru.katok.tamctf.api.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.katok.tamctf.api.util.GenericResponse;

@RestController
@RequestMapping("/api/v1")
public class GameController {

    private final Logger log = LoggerFactory.getLogger(getClass());


    @GetMapping(path = "/config", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse getConfig() {
        return new GenericResponse();
    }

    @GetMapping(path = "/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse getActiveTasks() {
        return new GenericResponse();
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
