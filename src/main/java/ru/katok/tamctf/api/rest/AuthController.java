package ru.katok.tamctf.api.rest;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.katok.tamctf.api.dto.LoginDto;
import ru.katok.tamctf.api.dto.SignUpDto;
import ru.katok.tamctf.domain.dto.UserDto;
import ru.katok.tamctf.domain.entity.UserEntity;
import ru.katok.tamctf.domain.error.EmailExistsException;
import ru.katok.tamctf.domain.util.MappingUtil;
import ru.katok.tamctf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import ru.katok.tamctf.api.util.GenericResponse;

import java.security.Principal;
import java.util.List;

@SuppressWarnings("ALL")
@RestController
@RequestMapping("/api/v1")
public class AuthController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping(path = "/signup",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse addUser(@Valid SignUpDto signUpDto) {

        LOGGER.debug("Registering user account with information: {}", signUpDto);

        // TODO: handle errors
        try {
            userService.registerNewUserAccount(signUpDto);
        } catch (EmailExistsException e) {
            return new GenericResponse(String.valueOf(e), e.getMessage());
        }

        return new GenericResponse("success");
    }


    @PostMapping("/login")
    public @ResponseBody GenericResponse  authenticateUser(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new GenericResponse("User log in successfully!...");
    }

    @GetMapping(path = "/me",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody UserDto getMyInfo(@AuthenticationPrincipal UserDetails user) {
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        var userEntity = userService.findUserByUsername(user.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found!")) ;
        var userDto = MappingUtil.mapToUserDto(userEntity);
        return userDto;
    }

}
