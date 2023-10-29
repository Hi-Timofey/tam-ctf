package ru.katok.tamctf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.katok.tamctf.domain.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
