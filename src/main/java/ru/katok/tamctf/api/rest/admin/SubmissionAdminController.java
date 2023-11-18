package ru.katok.tamctf.api.rest.admin;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
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

/*    @GetMapping(path = "hints/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse<TeamDto> deleteHint(@PathVariable Long id) {
        return new GenericResponse<>(true, "ok", teamService.getTeamById(id));
    }*/
}
