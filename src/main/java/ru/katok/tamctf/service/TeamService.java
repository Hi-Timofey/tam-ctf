package ru.katok.tamctf.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.katok.tamctf.domain.dto.TeamDto;
import ru.katok.tamctf.domain.entity.Team;
import ru.katok.tamctf.domain.entity.TeamType;
import ru.katok.tamctf.domain.entity.UserEntity;
import ru.katok.tamctf.domain.error.TeamAlreadyExistException;
import ru.katok.tamctf.domain.error.UserAlreadyExistException;
import ru.katok.tamctf.domain.error.UserNotFoundException;
import ru.katok.tamctf.repository.TeamRepository;
import ru.katok.tamctf.repository.UserRepository;
import ru.katok.tamctf.service.interfaces.ITeamService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service("teamService")
public class TeamService implements ITeamService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;


    @Override
    public List<Team> getAll() {
        return teamRepository.findAll();
    }

    @Override
    public Team createNewTeamWithCaptainName(TeamDto newTeam, String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);

        //TODO: make it with usage of  Optional
        //user.ifPresent((userEntity -> {
        //}));

        if (user.isEmpty()) throw new UserNotFoundException("There is no account with that nickname: " + username);

        UserEntity userEntity = user.get();

        TeamType type = newTeam.getType();
        if (TeamType.MIXED != type && newTeam.getUniversity() == null) {
            throw new UserAlreadyExistException("A team of type %s can't have no educational institution.".formatted(type));

        }


        Team team = createNewTeam(newTeam);
        //TODO: Set user role "CAPTAIN" AND add user to team
        //team.setUsers(Set.of(userEntity));
        return team;
    }

    @Override
    public Team createNewTeam(TeamDto newTeam) throws TeamAlreadyExistException {
        if (teamRepository.existsByName(newTeam.getName())) {
            throw new TeamAlreadyExistException("A team with that name already exists.");
        }
        Team team = Team.builder().teamType(newTeam.getType()).name(newTeam.getName()).university(newTeam.getUniversity()).build();
        return teamRepository.save(team);
    }
}
