package ru.katok.tamctf.api.rest.admin;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.katok.tamctf.api.util.GenericResponse;
import ru.katok.tamctf.domain.dto.SubmissionDto;
import ru.katok.tamctf.domain.dto.TaskDto;
import ru.katok.tamctf.domain.dto.TeamDto;
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

    @PostMapping(path = "/create-submission", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse<SubmissionDto> createSubmission(@RequestBody SubmissionDto newSubmission) {
        SubmissionDto submission = submissionService.createNewSubmission(newSubmission);
        return new GenericResponse<>(true, "ok", submission);
    }


    @DeleteMapping(path = "submissions/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse deleteSubmission(@PathVariable Long id) {
        this.submissionService.deleteSubmissionById(id);
        return new GenericResponse<>(true, "ok");
    }
}
