package ru.katok.tamctf.api.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.katok.tamctf.api.util.GenericResponse;
import ru.katok.tamctf.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class GameController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;


    @GetMapping(path = "/config", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse getConfig() {
        return new GenericResponse(false, "not implemented");
    }

    @GetMapping(path = "/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse getActiveTasks() {
        return new GenericResponse(false, "not implemented");
    }
    @GetMapping(path = "/scoreboard", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse getScoreboard() {
        return new GenericResponse(false, "not implemented");
    }

    @PostMapping(path = "/solve",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse solveTask(@AuthenticationPrincipal UserDetails user) {
        return new GenericResponse(false, "not implemented");
    }
}
