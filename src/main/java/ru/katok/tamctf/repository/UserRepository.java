package ru.katok.tamctf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.katok.tamctf.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

}
