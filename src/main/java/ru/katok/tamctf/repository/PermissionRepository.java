package ru.katok.tamctf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.katok.tamctf.domain.entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findByName(String name);
}
