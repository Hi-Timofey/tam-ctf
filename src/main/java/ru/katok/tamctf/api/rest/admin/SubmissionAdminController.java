package ru.katok.tamctf.api.rest.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
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

    @PatchMapping(path = "submissions/{id}",
            consumes = "application/json-patch+json",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody GenericResponse<SubmissionDto> editSubmissionById(
            @RequestBody JsonPatch patch, @PathVariable Long id)
            throws JsonPatchException, JsonProcessingException {
        SubmissionDto submission = submissionService.editSubmissionById(id, patch);
        return new GenericResponse<>(true, "UPDATED", submission);
    }

    @DeleteMapping(path = "submissions/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse deleteSubmission(@PathVariable Long id) {
        this.submissionService.deleteSubmissionById(id);
        return new GenericResponse<>(true, "Submission has been deleted");
    }
}
