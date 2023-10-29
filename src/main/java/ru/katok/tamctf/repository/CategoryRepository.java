package ru.katok.tamctf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.katok.tamctf.domain.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    boolean existsByName(String name);
}
