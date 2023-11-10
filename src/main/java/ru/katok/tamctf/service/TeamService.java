package ru.katok.tamctf.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.katok.tamctf.domain.dto.TeamDto;
import ru.katok.tamctf.domain.entity.Team;
import ru.katok.tamctf.domain.entity.TeamType;
import ru.katok.tamctf.domain.entity.UserEntity;
import ru.katok.tamctf.domain.error.TeamAlreadyExistException;
import ru.katok.tamctf.domain.error.TeamNotFoundException;
import ru.katok.tamctf.domain.error.UserAlreadyExistException;
import ru.katok.tamctf.domain.error.UserNotFoundException;
import ru.katok.tamctf.repository.RoleRepository;
import ru.katok.tamctf.domain.util.MappingUtil;
import ru.katok.tamctf.repository.TeamRepository;
import ru.katok.tamctf.repository.UserRepository;
import ru.katok.tamctf.service.interfaces.ITeamService;

import java.util.*;

@RequiredArgsConstructor
@Service("teamService")
public class TeamService implements ITeamService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Override
    public List<TeamDto> getAll() {
        return teamRepository.findAll().stream()
                .map(MappingUtil::mapToTeamDto).toList();
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public TeamDto createNewTeamWithCaptainName(TeamDto newTeam, String username) {
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

        if (teamRepository.existsByName(newTeam.getName())) {
            throw new TeamAlreadyExistException("A team with that name already exists.");
        }

        // TODO: Fix bidirectional adding
        Team team = Team.builder()
                .teamType(newTeam.getType())
                .name(newTeam.getName())
                .university(newTeam.getUniversity())
                .build();
        team = teamRepository.save(team);

        userEntity.setTeam(team);


        userEntity.addRole(roleRepository.findByName("ROLE_TEAM_CAPTAIN"));
        userRepository.save(userEntity);

        Set<UserEntity> members = new HashSet<>();
        members.add(userEntity);
        team.setUsers(members);

        return  MappingUtil.mapToTeamDto(team);
    }

    @Override
    public TeamDto getTeamById(Long id) throws TeamNotFoundException {
        return MappingUtil.mapToTeamDto( teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException("no such team with id: " + id)));
    }

    @Override
    public void joinTeamWithToken(String inviteCode, String username) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);

        //TODO: make it with usage of  Optional
        //user.ifPresent((userEntity -> {
        //}));

        if (userEntity.isEmpty()) throw new UserNotFoundException("There is no account with that nickname: " + username);

        UserEntity user = userEntity.get();

        if (user.getTeam() != null) throw new TeamAlreadyExistException("You are already on the team.");

        Optional<Team> teamEntity = teamRepository.findTeamByInviteCode(inviteCode);

        if (teamEntity.isEmpty()) throw new TeamNotFoundException("Invalid invite code.");
        Team team = teamEntity.get();
        //TODO: team.addUserToTeam(user);
        teamRepository.save(team);
    }
}
