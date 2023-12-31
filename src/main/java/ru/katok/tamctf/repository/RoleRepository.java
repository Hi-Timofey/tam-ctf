package ru.katok.tamctf.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.katok.tamctf.domain.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    RoleEntity findByName(String name);

    @Override
    void delete(@NonNull RoleEntity role);

}
