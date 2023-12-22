package ru.katok.tamctf.api.rest;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
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


    private final UserService userService;

    @PostMapping(path = "/signup",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody GenericResponse<UserDto> addUserUsingJSON(
            @RequestBody @Valid SignUpDto signUpDto) {
        String ipAddress = ((WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getRemoteAddress();
        // TODO: handle errors
        UserDto userDto;
        try {
            userDto = userService.registerNewUserAccount(signUpDto, ipAddress);
        } catch (UserAlreadyExistException | EmailExistsException e) {
            return new GenericResponse(e);
        }
        log.debug("Registered user account with information: {}", signUpDto);
        return new GenericResponse(true, "success", userDto);
    }


    @PostMapping(path = "/login",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody GenericResponse<UserDto> authenticateUser(
            @RequestBody LoginDto loginDto, HttpServletRequest request) {
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();
        try {

            request.login(username, password);

            return new GenericResponse<>(true, "User log in successfully!...", userService.findUserByUsername(loginDto.getUsername()));
        } catch (ServletException e) {
            return new GenericResponse<>(false, "Invalid username or password");

        }
    }

    /**
     * Changes user password if and only old password matches one stored in db.
     *
     * @param changePasswordDto old user password with new password provided
     * @param user              auth principal
     * @return GenericResponse
     */
    @PostMapping("/change-password")
    public @ResponseBody GenericResponse<Boolean> changeUserPassword(
            @RequestBody @Valid ChangePasswordDto changePasswordDto,
            @AuthenticationPrincipal UserDetails user) {
        final String oldPass = changePasswordDto.getOldPassword();
        final String newPass = changePasswordDto.getNewPassword();
        final String username = user.getUsername();
        boolean done = userService.changeUserPassword(username,oldPass, newPass);
        return new GenericResponse<Boolean>(done, "Result of changing password", done);
    }

    @GetMapping(path = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse<UserDto> getMyInfo(@AuthenticationPrincipal UserDetails user) {
        return new GenericResponse<>(true, "ok", userService.findUserByUsername(user.getUsername()));
    }

}
