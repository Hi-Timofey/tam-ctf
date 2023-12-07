package ru.katok.tamctf.api.rest.admin;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.katok.tamctf.api.dto.admin.EditUserDto;
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

    @PatchMapping(path = "users/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody GenericResponse<EditUserDto> editUserById(@RequestBody EditUserDto userDto, @PathVariable Long id){
        // TODO: Write code here
        // UserDto user = userService.editUserById(id, userDto);
        return new GenericResponse<>( false, "Not implemented", userDto/*user*/);
    }

    @GetMapping(path = "users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse<UserDto> getUserById(@PathVariable Long id) {
        return new GenericResponse<>( true, "ok", userService.getUserById(id));
    }

    @DeleteMapping(path = "users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return new GenericResponse<>(true, "User has been deleted");
    }

}
