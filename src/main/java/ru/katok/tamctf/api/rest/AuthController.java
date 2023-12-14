package ru.katok.tamctf.api.rest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.katok.tamctf.api.dto.ChangePasswordDto;
import ru.katok.tamctf.api.dto.LoginDto;
import ru.katok.tamctf.api.dto.SignUpDto;
import ru.katok.tamctf.api.util.GenericResponse;
import ru.katok.tamctf.domain.dto.UserDto;
import ru.katok.tamctf.domain.error.EmailExistsException;
import ru.katok.tamctf.domain.error.UserAlreadyExistException;
import ru.katok.tamctf.service.UserService;

@SuppressWarnings("ALL")
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class AuthController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    @PostMapping(path = "/signup", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public @ResponseBody GenericResponse<UserDto> addUserUsingForm(@Valid SignUpDto signUpDto) {

        // TODO: handle errors
        UserDto userDto;
        try {
            userDto = userService.registerNewUserAccount(signUpDto);
        } catch (UserAlreadyExistException | EmailExistsException e) {
            return new GenericResponse(e);
        }
        log.debug("Registered user account with information: {}", signUpDto);
        return new GenericResponse(true, "success", userDto);
    }

    @PostMapping(path = "/signup",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody GenericResponse<UserDto> addUserUsingJSON(
            @RequestBody @Valid SignUpDto signUpDto) {

        // TODO: handle errors
        UserDto userDto;
        try {
            userDto = userService.registerNewUserAccount(signUpDto);
        } catch (UserAlreadyExistException | EmailExistsException e) {
            return new GenericResponse(e);
        }
        log.debug("Registered user account with information: {}", signUpDto);
        return new GenericResponse(true, "success", userDto);
    }


    @PostMapping("/login")
    public @ResponseBody GenericResponse authenticateUser(
            @RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new GenericResponse(true, "User log in successfully!...");
    }

    /**
     * Changes user password if and only old password matches one stored in db.
     *
     * @param changePasswordDto old user password with new password provided
     * @param user              auth principal
     * @return GenericResponse
     */
    @PostMapping("/change-password")
    public @ResponseBody GenericResponse changeUserPassword(
            @RequestBody ChangePasswordDto changePasswordDto,
            @AuthenticationPrincipal UserDetails user) {
        final String oldPass = changePasswordDto.getOldPassword();
        final String newPass = changePasswordDto.getNewPassword();

        //TODO: Implement via 1 simple call to service
//        var userEntity = userService.findUserByUsername(user.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
//
//        if (userService.checkIfValidPassword(userEntity, oldPass)) {
//            userService.changeUserPassword(userEntity, newPass);
//            return new GenericResponse(true, "ok");
//        }
//        return new GenericResponse(false, "Incorrect password");
        return new GenericResponse(false, "not implemented");
    }

    @GetMapping(path = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse<UserDto> getMyInfo(@AuthenticationPrincipal UserDetails user) {
        return new GenericResponse<>(true, "ok", userService.findUserByUsername(user.getUsername()));
    }

}
