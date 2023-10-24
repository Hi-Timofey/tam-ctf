package ru.katok.tamctf.api.rest;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.katok.tamctf.api.dto.ChangePasswordDto;
import ru.katok.tamctf.api.dto.LoginDto;
import ru.katok.tamctf.api.dto.SignUpDto;
import ru.katok.tamctf.api.util.GenericResponse;
import ru.katok.tamctf.domain.dto.UserDto;
import ru.katok.tamctf.domain.error.EmailExistsException;
import ru.katok.tamctf.domain.error.UserAlreadyExistException;
import ru.katok.tamctf.domain.util.MappingUtil;
import ru.katok.tamctf.service.UserService;

@SuppressWarnings("ALL")
@RestController
@RequestMapping("/api/v1")
public class AuthController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping(path = "/signup", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse addUser(@Valid SignUpDto signUpDto) {

        LOGGER.debug("Registering user account with information: {}", signUpDto);

        // TODO: handle errors
        try {
            userService.registerNewUserAccount(signUpDto);
        } catch (UserAlreadyExistException | EmailExistsException e) {
            return new GenericResponse(e);
        }

        return new GenericResponse(true, "success");
    }


    @PostMapping("/login")
    public @ResponseBody GenericResponse authenticateUser(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new GenericResponse(true, "User log in successfully!...");
    }

    /**
     * Changes user password if and only old password matches one stored in db.
     * TODO: Test if working correctly
     *
     * @param changePasswordDto old user password with new password provided
     * @param user              auth principal
     * @return GenericResponse
     */
    @PostMapping("/change-password")
    public @ResponseBody GenericResponse changeUserPassword(@RequestBody ChangePasswordDto changePasswordDto, @AuthenticationPrincipal UserDetails user) {
        final String oldPass = changePasswordDto.getOldPassword();
        final String newPass = changePasswordDto.getNewPassword();

        var userEntity = userService.findUserByUsername(user.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        if (userService.checkIfValidPassword(userEntity, oldPass)) {
            userService.changeUserPassword(userEntity, newPass);
            return new GenericResponse(true, "ok");
        }
        return new GenericResponse(false, "Incorrect password");
    }

    @GetMapping(path = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody UserDto getMyInfo(@AuthenticationPrincipal UserDetails user) {
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        var userEntity = userService.findUserByUsername(user.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        var userDto = MappingUtil.mapToUserDto(userEntity);
        return userDto;
    }

}
