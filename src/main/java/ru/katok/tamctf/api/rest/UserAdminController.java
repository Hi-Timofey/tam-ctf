package ru.katok.tamctf.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.katok.tamctf.api.util.GenericResponse;
import ru.katok.tamctf.domain.entity.UserEntity;
import ru.katok.tamctf.service.UserService;

import java.util.List;

@SuppressWarnings("ALL")
@RestController()
@RequestMapping("/api/v1/admin")
public class UserAdminController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "users",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<UserEntity> getAllUsers() {
        return this.userService.getAll();
    }

    @PostMapping(path = "users",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody UserEntity newUser(@RequestBody UserEntity newUser) {
        return this.userService.save(newUser);
    }

    @GetMapping(path = "users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody UserEntity getUserById(@PathVariable Long id) {
        return this.userService.getById(id);
    }

    @DeleteMapping(path = "users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse deleteUser(@PathVariable Long id) {
        this.userService.deleteUser(this.userService.getById(id));
        return new GenericResponse("ok");
    }

}
