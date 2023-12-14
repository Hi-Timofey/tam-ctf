package ru.katok.tamctf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.katok.tamctf.domain.entity.Submission;
import ru.katok.tamctf.domain.entity.Task;
import ru.katok.tamctf.domain.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    Optional<Submission> findByUser(UserEntity username);

    @Query("select case when (count(s) > 0 ) then true else false end " +
           "from Submission s where s.task.name = ?1 and s.team.id = ?2 and s.isSuccessful = true")
    boolean findSolvesByTeam(String taskName, Long teamId);

    //findAllBySuccessfulTrueAndTaskIs
    @Query("select s from Submission s where s.isSuccessful = true and s.task = ?1")
    List<Submission> findAllSuccsessfulByTask(Task task);
    Integer countAllByIsSuccessfulIsTrueAndTaskId(Long taskId);
}
