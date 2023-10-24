package ru.katok.tamctf.api.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.katok.tamctf.api.util.GenericResponse;
import ru.katok.tamctf.service.UserService;


@RestController
@RequestMapping("/api/v1")
public class TeamController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;


    @PostMapping(path = "/create-team",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse createTeam(@AuthenticationPrincipal UserDetails user) {
        var userEntity = userService.findUserByUsername(user.getUsername()).orElseThrow(()
                -> new UsernameNotFoundException("User not found!"));

        if (userEntity.getTeam() != null) {
            LOGGER.debug("User tried to create team while he is already on the team.");
            return new GenericResponse(false, "You are already on the team");
        }

        return new GenericResponse(true, "ok");
    }


    @PostMapping(path = "/join-team",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse joinTeam(@AuthenticationPrincipal UserDetails user) {
        return new GenericResponse(false, "not implemented");
    }

    @GetMapping(path = "/list-team",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse listTeam(@AuthenticationPrincipal UserDetails user) {
        return new GenericResponse(false, "not implemented");
    }


    @PostMapping(path = "/remove-user-from-team",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse removeUserFromTeam(@AuthenticationPrincipal UserDetails user) {
        return new GenericResponse(false, "not implemented");
    }
}
