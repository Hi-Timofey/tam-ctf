package ru.katok.tamctf.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.katok.tamctf.domain.dto.SubmissionDto;
import ru.katok.tamctf.domain.entity.Submission;
import ru.katok.tamctf.domain.entity.UserEntity;
import ru.katok.tamctf.domain.error.UserNotFoundException;
import ru.katok.tamctf.domain.util.MappingUtil;
import ru.katok.tamctf.repository.SubmissionRepository;
import ru.katok.tamctf.repository.UserRepository;
import ru.katok.tamctf.service.interfaces.ISubmissionService;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("ALL")
@RequiredArgsConstructor
@Service("submissionService")
public class SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;

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

    public void deleteSubmissionById(Long id){
        submissionRepository.delete(submissionRepository.getById(id));
    }
}
