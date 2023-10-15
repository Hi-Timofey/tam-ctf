package ru.katok.tamctf.api.rest;

import org.springframework.http.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.katok.tamctf.api.dto.LoginDto;
import ru.katok.tamctf.api.dto.SignUpDto;
import ru.katok.tamctf.domain.entity.UserEntity;
import ru.katok.tamctf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import ru.katok.tamctf.api.util.GenericResponse;

@RestController
public class AuthController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping(path = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse addUser(@RequestBody SignUpDto signUpDto) {

        LOGGER.debug("Registering user account with information: {}", signUpDto);

        // TODO: handle errors
        final UserEntity registred = userService.registerNewUserAccount(signUpDto);

        return new GenericResponse("success");
    }


    @PostMapping("/login")
    public @ResponseBody GenericResponse  authenticateUser(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new GenericResponse("User login successfully!...");
    }

    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse loginUser(@RequestBody SignUpDto signUpDto) {

        LOGGER.debug("Registering user account with information: {}", signUpDto);

        // TODO: handle errors
        final UserEntity registred = userService.registerNewUserAccount(signUpDto);

        return new GenericResponse("success");
    }
}
