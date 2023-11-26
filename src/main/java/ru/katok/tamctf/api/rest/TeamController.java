package ru.katok.tamctf.api.rest;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.katok.tamctf.api.dto.InviteCodeDto;
import ru.katok.tamctf.api.util.GenericResponse;
import ru.katok.tamctf.domain.dto.TeamDto;
import ru.katok.tamctf.domain.entity.Team;
import ru.katok.tamctf.service.TeamService;
import ru.katok.tamctf.service.UserService;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class TeamController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final TeamService teamService;


    @PostMapping(path = "/create-team", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse<TeamDto> createTeam(@RequestBody TeamDto newTeam ,@AuthenticationPrincipal UserDetails user) {
        String username = user.getUsername();
        log.debug("TeamDto value: {}", newTeam);
        TeamDto team = teamService.createNewTeamWithCaptainName(newTeam, username);
        return new GenericResponse<>(true, "ok", team);
    }


    @PostMapping(path = "/join-team", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse<Boolean> joinTeam(@RequestBody InviteCodeDto inviteCodeDto, @AuthenticationPrincipal UserDetails user) {
        String username = user.getUsername();
        String inviteCode = inviteCodeDto.getInviteCode();
        boolean done = teamService.joinTeamWithToken(inviteCode, username);
        return new GenericResponse<>(true, "ok", done);
    }

    @GetMapping(path = "/list-team", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse<List<TeamDto>> listTeam(@AuthenticationPrincipal UserDetails user) {
        return new GenericResponse<>(true, "ok", teamService.getAll());
    }


    @PostMapping(path = "/remove-user-from-team", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse<Boolean> removeUserFromTeam(@AuthenticationPrincipal UserDetails user) {
        return new GenericResponse<>(true,"ok");
    }
}
