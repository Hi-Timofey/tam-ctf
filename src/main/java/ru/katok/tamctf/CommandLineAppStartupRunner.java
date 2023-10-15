package ru.katok.tamctf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.katok.tamctf.domain.entity.Role;
import ru.katok.tamctf.domain.entity.UserEntity;
import ru.katok.tamctf.repository.UserRepository;
import ru.katok.tamctf.service.UserService;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    @Autowired
    UserService service;

    @Override
    public void run(String...args) {
        try {
            this.service.findUserByUsername("admin").orElseThrow();
        } catch (Exception e){
            UserEntity admin = UserEntity.builder()
                    .email("admin@mail.ru")
                    .username("admin") // default password - admin
                    .password("$2a$11$xlxxjUZTEin4SwWzygU7b.XpGJd9IJKzutgwblapIKJWu/CPBqKWi")
                    .role(Role.ADMIN)
                    .isActive(true)
                    .build();
            this.service.save(admin);
        }

    }
}
