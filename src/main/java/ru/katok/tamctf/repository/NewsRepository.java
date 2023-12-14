package ru.katok.tamctf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.katok.tamctf.domain.entity.News;

public interface NewsRepository extends JpaRepository<News, Long> {
}
