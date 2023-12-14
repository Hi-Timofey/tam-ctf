package ru.katok.tamctf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.katok.tamctf.domain.entity.Hint;

public interface HintRepository extends JpaRepository<Hint, Long> {

}
