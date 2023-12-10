package ru.katok.tamctf.api.rest.admin;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.katok.tamctf.api.util.GenericResponse;
import ru.katok.tamctf.domain.dto.TeamDto;
import ru.katok.tamctf.service.TeamService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class TeamAdminController {
    private final TeamService teamService;

    @ResponseBody
    @GetMapping(path = "teams", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse<List<TeamDto>> getAllTeams() {
        return new GenericResponse<>(true, "ok", teamService.getAll()) ;
    }


    @GetMapping(path = "teams/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse<TeamDto> getTeamById(@PathVariable Long id) {
        return new GenericResponse<>(true, "ok", teamService.getTeamById(id));
    }

    @PostMapping(path = "teams", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse<TeamDto>createTeam(@RequestBody TeamDto newTeam){
        TeamDto team = teamService.createNewTeam(newTeam);
        return new GenericResponse<>(true, "Team has been created");
    }
    @DeleteMapping(path = "teams/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse deleteTeam(@PathVariable Long id) {
        this.teamService.deleteTeam(id);
        return new GenericResponse<>(true, "Team has been deleted");
    }
}
