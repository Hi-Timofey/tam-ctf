package ru.katok.tamctf.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.katok.tamctf.domain.dto.SubmissionDto;
import ru.katok.tamctf.domain.entity.Submission;
import ru.katok.tamctf.domain.entity.UserEntity;
import ru.katok.tamctf.domain.error.UserNotFoundException;
import ru.katok.tamctf.domain.util.MappingUtil;
import ru.katok.tamctf.repository.SubmissionRepository;
import ru.katok.tamctf.repository.TaskRepository;
import ru.katok.tamctf.repository.TeamRepository;
import ru.katok.tamctf.repository.UserRepository;
import ru.katok.tamctf.service.interfaces.ISubmissionService;

import java.util.List;

@SuppressWarnings("ALL")
@RequiredArgsConstructor
@Service("submissionService")
public class SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TeamRepository teamRepository;

    public List<SubmissionDto> getAll() {
        return submissionRepository.findAll().stream()
                .map(MappingUtil::mapToSubmissionDto).toList();
    }
    public List<SubmissionDto> findAllSubsByUser(String username){
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() ->
                new UserNotFoundException("no such user with username: {}".format(username)));
        return submissionRepository.findByUser(user).stream()
                .map(MappingUtil::mapToSubmissionDto).toList();
    }

    public SubmissionDto createNewSubmission(SubmissionDto submissionDto){
        Submission submission = Submission.builder()
                .isSuccessful(submissionDto.isSuccessful())
                .flag(submissionDto.getFlag())
                .solverIp(submissionDto.getSolverIp())
                .task(taskRepository.getById(submissionDto.getTaskId()))
                .user(userRepository.getById(submissionDto.getUserId()))
                .team(teamRepository.getById(submissionDto.getTeamId()))
                .build();
        return MappingUtil.mapToSubmissionDto(submissionRepository.save(submission));
    }
    @Override

    public void deleteSubmissionById(Long id){
        submissionRepository.delete(submissionRepository.getById(id));
    }
}
