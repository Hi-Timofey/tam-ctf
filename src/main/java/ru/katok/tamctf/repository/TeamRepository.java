package ru.katok.tamctf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.katok.tamctf.domain.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

}
