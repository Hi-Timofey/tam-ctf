package ru.katok.tamctf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.katok.tamctf.domain.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
