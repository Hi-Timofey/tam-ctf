package ru.katok.tamctf.service.interfaces;

import ru.katok.tamctf.domain.dto.TeamDto;
import ru.katok.tamctf.domain.dto.UserDto;
import ru.katok.tamctf.domain.entity.Team;
import ru.katok.tamctf.domain.entity.UserEntity;
import ru.katok.tamctf.domain.error.TeamAlreadyExistException;
import ru.katok.tamctf.domain.error.TeamNotFoundException;
import ru.katok.tamctf.domain.error.TeamTypeError;
import ru.katok.tamctf.domain.error.UserNotFoundException;

import java.util.List;
import java.util.Set;

public interface ITeamService {
    List<TeamDto> getAll();

    TeamDto createNewTeamWithCaptainName(TeamDto newTeam, String username) throws TeamTypeError;

    TeamDto getTeamById(Long id) throws TeamNotFoundException;

    void joinTeamWithToken(String inviteCode, String username);
}
