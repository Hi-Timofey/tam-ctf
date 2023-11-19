package ru.katok.tamctf.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.katok.tamctf.domain.dto.SubmissionDto;
import ru.katok.tamctf.domain.util.MappingUtil;
import ru.katok.tamctf.repository.SubmissionRepository;
import ru.katok.tamctf.service.interfaces.ISubmissionService;

import java.util.List;


@SuppressWarnings("ALL")
@RequiredArgsConstructor
@Service("submissionService")
public class SubmissionService {
    private final SubmissionRepository submissionRepository;


    public List<SubmissionDto> getAll() {
        return submissionRepository.findAll().stream()
                .map(MappingUtil::mapToSubmissionDto).toList();
    }
}
