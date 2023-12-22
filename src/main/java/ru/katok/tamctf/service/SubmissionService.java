package ru.katok.tamctf.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
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

import java.util.List;
import java.util.Optional;

@SuppressWarnings("ALL")
@RequiredArgsConstructor
@Service("submissionService")
public class SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TeamRepository teamRepository;
    private final ObjectMapper objectMapper;

    public List<SubmissionDto> getAll() {
        return submissionRepository.findAll().stream()
                .map(MappingUtil::mapToSubmissionDto).toList();
    }

    public List<SubmissionDto> findAllSubsByUser(String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() ->
                new UserNotFoundException("no such user with username: {}".format(username)));
        return submissionRepository.findByUser(user).stream()
                .map(MappingUtil::mapToSubmissionDto).toList();
    }

    public SubmissionDto createNewSubmission(SubmissionDto submissionDto) {
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

    public Submission applyPatchToSubmission(JsonPatch patch, Submission submission) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(submission, JsonNode.class));
        return objectMapper.treeToValue(patched, Submission.class);
    }


    public SubmissionDto editSubmissionById(Long id, JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        Optional<Submission> submission = submissionRepository.findById(id);
        Submission sub = submission.get();
        Submission patchedSubmission = submissionRepository.save(applyPatchToSubmission(patch, sub));
        return MappingUtil.mapToSubmissionDto(patchedSubmission);
    }



    public void deleteSubmissionById(Long id) {
        submissionRepository.delete(submissionRepository.getById(id));
    }
}
