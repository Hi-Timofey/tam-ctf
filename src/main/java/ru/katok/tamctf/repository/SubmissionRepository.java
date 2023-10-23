package ru.katok.tamctf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.katok.tamctf.domain.entity.Submission;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
}
