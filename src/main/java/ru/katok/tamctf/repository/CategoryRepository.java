package ru.katok.tamctf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.katok.tamctf.domain.entity.Category;
import ru.katok.tamctf.domain.entity.Task;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    boolean existsByName(String name);
    Optional<Category> findByName(String name);
    void deleteByName(String name);
}
