package ru.katok.tamctf.api.rest;

import org.springframework.http.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.katok.tamctf.api.dto.UserRestDto;
import ru.katok.tamctf.domain.entity.UserEntity;
import ru.katok.tamctf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import ru.katok.tamctf.api.util.GenericResponse;

@RestController
public class AuthController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @PostMapping(path = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse addUser(@RequestBody UserRestDto userRestDto) {

        LOGGER.debug("Registering user account with information: {}", userRestDto);

        // TODO: handle errors
        final UserEntity registred = userService.registerNewUserAccount(userRestDto);

        return new GenericResponse("success");
    }
}
