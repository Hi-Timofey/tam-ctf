package ru.katok.tamctf.api.rest.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.katok.tamctf.api.dto.admin.Ip;
import ru.katok.tamctf.api.util.GenericResponse;
import ru.katok.tamctf.domain.dto.UserDto;
import ru.katok.tamctf.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class UserAdminController {

    private final UserService userService;

    @GetMapping(path = "users", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse<List<UserDto>> getAllUsers() {
        return new GenericResponse<>(true, "ok", userService.getAll());
    }

    @PostMapping(path = "users", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse<UserDto> createUser(@RequestBody UserDto newUser) {
        UserDto user = userService.createNewUserAccount(newUser);
        return new GenericResponse<>(true, "New user has been created", user);
    }

    @PatchMapping(path = "users/{id}",
            consumes = "application/json-patch+json",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody GenericResponse<UserDto> editUserById(
            @RequestBody JsonPatch patch, @PathVariable Long id)
            throws JsonPatchException, JsonProcessingException {
        UserDto user = userService.editUserById(id, patch);
        return new GenericResponse<>(true, "UPDATED", user);
    }

    @GetMapping(path = "users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse<UserDto> getUserById(@PathVariable Long id) {
        return new GenericResponse<>(true, "ok", userService.getUserById(id));
    }

    @DeleteMapping(path = "users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return new GenericResponse<>(true, "User has been deleted");
    }

    @PostMapping(path = "users/banuser",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse banUser(@RequestBody Ip ip) {
        String targetIp = ip.getIp();
        userService.ipUserBan(targetIp);
        return new GenericResponse<>(true, "Ban result of user");
    }

    @GetMapping(path = "users/banned", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse<List<String>> getAllBannedUsers() {
        return new GenericResponse<>(true, "ok", userService.getAllBanned());
    }


}
