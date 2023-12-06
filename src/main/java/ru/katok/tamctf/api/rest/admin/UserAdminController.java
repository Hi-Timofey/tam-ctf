package ru.katok.tamctf.api.rest.admin;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.katok.tamctf.api.util.GenericResponse;
import ru.katok.tamctf.domain.dto.UserDto;
import ru.katok.tamctf.service.UserService;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class UserAdminController {

    private final UserService userService;

    @GetMapping(path = "users", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse<List<UserDto>> getAllUsers() {
        return new GenericResponse<>(true, "ok", userService.getAll());
    }
//    @PostMapping(path = "users", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public @ResponseBody UserEntity newUser(@RequestBody UserEntity newUser) {
//        return userService.save(newUser);
//    }

    @GetMapping(path = "user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse<UserDto> getUserById(@PathVariable Long id) {
        return new GenericResponse<>( true, "ok", userService.getUserById(id));
    }

    @DeleteMapping(path = "delete-user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return new GenericResponse<>(true, "ok");
    }

}
