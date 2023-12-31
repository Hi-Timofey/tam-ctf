package ru.katok.tamctf.service.interfaces;

import ru.katok.tamctf.domain.dto.SubmissionDto;

import java.util.List;

public interface ISubmissionService {

    List<SubmissionDto> getAll();

/*    List<SubmissionDto> findAllSubsByUser(String username);*/

    SubmissionDto createNewSubmission(SubmissionDto submissionDto);

    void deleteSubmissionById(Long id);
}
