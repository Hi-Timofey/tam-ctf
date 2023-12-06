
package ru.katok.tamctf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.katok.tamctf.domain.entity.File;

public interface FileRepository extends JpaRepository<File, Long> {


}

