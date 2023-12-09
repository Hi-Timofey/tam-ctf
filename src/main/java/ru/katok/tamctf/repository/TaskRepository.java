package ru.katok.tamctf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.katok.tamctf.domain.entity.Task;
import ru.katok.tamctf.domain.entity.UserEntity;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    boolean existsByName(String name);

    Optional<Task> findByName(String name);

    Optional<Task>  findByActiveTrueAndFlagIs(String flag);
}
