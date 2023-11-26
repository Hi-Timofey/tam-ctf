package ru.katok.tamctf.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.katok.tamctf.domain.dto.SubmissionDto;
import ru.katok.tamctf.domain.entity.Submission;
import ru.katok.tamctf.domain.util.MappingUtil;
import ru.katok.tamctf.repository.SubmissionRepository;
import ru.katok.tamctf.service.interfaces.ISubmissionService;

import java.util.List;


@SuppressWarnings("ALL")
@RequiredArgsConstructor
@Service("submissionService")
public class SubmissionService implements ISubmissionService{
    private final SubmissionRepository submissionRepository;


    public List<SubmissionDto> getAll() {
        return submissionRepository.findAll().stream()
                .map(MappingUtil::mapToSubmissionDto).toList();
    }
    public List<SubmissionDto> findAllSubsByUser(String username){
        return submissionRepository.findByUsername(username).stream()
                .map(MappingUtil::mapToSubmissionDto).toList();
    }

    public SubmissionDto createNewSubmission(SubmissionDto submissionDto){
        Submission submission = Submission.builder()
                .isSuccessful(submissionDto.isSuccessful())
                .flag(submissionDto.getFlag())
                .solverIp(submissionDto.getSolverIp())
                .task(submissionDto.getTask())
                .user(submissionDto.getUser())
                .team(submissionDto.getTeam())
                .build();
        return MappingUtil.mapToSubmissionDto(submissionRepository.save(submission));
    }

    public void deleteSubmissionById(Long id){
        submissionRepository.delete(submissionRepository.getById(id));
    }
}
