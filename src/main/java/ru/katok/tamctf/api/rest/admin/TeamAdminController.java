package ru.katok.tamctf.api.rest.admin;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.katok.tamctf.api.util.GenericResponse;
import ru.katok.tamctf.domain.entity.Team;
import ru.katok.tamctf.service.TeamService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class TeamAdminController {
    private final TeamService teamService;

    @ResponseBody
    @GetMapping(path = "teams", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse<List<Team>> getAllTeams() {
        return new GenericResponse<>(true, "ok", teamService.getAll()) ;
    }


    @DeleteMapping(path = "teams/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse deleteUser(@PathVariable Long id) {
        this.teamService.deleteTeamById(id);
        return new GenericResponse<>(true, "ok");
    }
}
