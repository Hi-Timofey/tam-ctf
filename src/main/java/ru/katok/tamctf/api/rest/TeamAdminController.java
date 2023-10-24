package ru.katok.tamctf.api.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
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
    public List<Team> getAllTeams() {
        return teamService.getAll();
    }
}
