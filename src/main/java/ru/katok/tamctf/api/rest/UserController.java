package ru.katok.tamctf.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.katok.tamctf.domain.entity.UserEntity;
import ru.katok.tamctf.service.UserService;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/admin")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "users",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<UserEntity> getAll() {
        return this.userService.getAll();
    }

    @PostMapping(path = "users",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody UserEntity newEmployee(@RequestBody UserEntity newUser) {
        return this.userService.save(newUser);
    }


}
