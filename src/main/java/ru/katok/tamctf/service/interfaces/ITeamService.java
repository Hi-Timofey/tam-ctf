package ru.katok.tamctf.service.interfaces;

import ru.katok.tamctf.domain.dto.TeamDto;
import ru.katok.tamctf.domain.entity.Team;
import ru.katok.tamctf.domain.error.TeamAlreadyExistException;
import ru.katok.tamctf.domain.error.TeamTypeError;

import java.util.List;

public interface ITeamService {
    List<Team> getAll();

    Team createNewTeamWithCaptainName(TeamDto newTeam, String username) throws TeamTypeError;

    Team createNewTeam(TeamDto newTeam) throws TeamAlreadyExistException;

    void joinTeamWithToken(String inviteCode, String username);

    void removeUserFromTeam();
}
