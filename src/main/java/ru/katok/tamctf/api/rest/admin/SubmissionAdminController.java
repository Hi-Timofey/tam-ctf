package ru.katok.tamctf.api.rest.admin;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.katok.tamctf.api.util.GenericResponse;
import ru.katok.tamctf.domain.dto.SubmissionDto;
import ru.katok.tamctf.service.SubmissionService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class SubmissionAdminController {

    private final SubmissionService submissionService;

    @ResponseBody
    @GetMapping(path = "submissions", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse<List<SubmissionDto>> getAllSubmissions() {
        return new GenericResponse<>(true, "ok", submissionService.getAll());
    }

    @DeleteMapping(path = "submissions/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse deleteSubmission(@PathVariable Long id) {
        this.submissionService.deleteSubmissionById(id);
        return new GenericResponse<>(true, "Submission has been deleted");
    }
}
