package ru.katok.tamctf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.katok.tamctf.domain.entity.Submission;
import ru.katok.tamctf.domain.entity.UserEntity;

import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    Optional<Submission> findByUser(UserEntity username);

    @Query("select case when (count(s) > 0 ) then true else false end " +
           "from Submission s where s.task.name = ?1 and s.team.id = ?2 and s.isSuccessful = true")
    boolean findSolvesByTeam(String taskName, Long teamId);

/*    List<Submission> findAllByTaskId(Task TaskId);*/
    Integer countAllByIsSuccessfulIsTrueAndTaskId(Long taskId);
}
