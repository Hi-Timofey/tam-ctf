package ru.katok.tamctf.service.interfaces;

import ru.katok.tamctf.domain.dto.TeamDto;
import ru.katok.tamctf.domain.dto.UserDto;
import ru.katok.tamctf.domain.error.TeamNotFoundException;
import ru.katok.tamctf.domain.error.TeamTypeError;

import java.util.List;

public interface ITeamService {
    List<TeamDto> getAll();


    List<UserDto> getAllTeamUsers(String username);

    TeamDto createNewTeamWithCaptainName(TeamDto newTeam, String username) throws TeamTypeError;

    TeamDto getTeamById(Long id) throws TeamNotFoundException;

    boolean joinTeamWithToken(String inviteCode, String username);

    boolean removeUserFromTeam(String username, String teamName);

    void deleteTeam(Long id);
}
