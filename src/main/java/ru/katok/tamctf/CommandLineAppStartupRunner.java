package ru.katok.tamctf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.katok.tamctf.domain.entity.Role;
import ru.katok.tamctf.domain.entity.UserEntity;
import ru.katok.tamctf.repository.UserRepository;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    @Autowired
    UserRepository repo;

    @Override
    public void run(String...args) {
        try {
            this.repo.findByUsername("admin").orElseThrow();
        } catch (Exception e){
            UserEntity admin = UserEntity.builder()
                    .email("admin@mail.ru")
                    .username("admin")
                    .password("123123")
                    .role(Role.ADMIN)
                    .isActive(true)
                    .build();
            this.repo.save(admin);
        }

    }
}
