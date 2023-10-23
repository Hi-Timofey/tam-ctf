package ru.katok.tamctf.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.katok.tamctf.domain.entity.Team;
import ru.katok.tamctf.repository.TeamRepository;
import ru.katok.tamctf.service.interfaces.ITeamService;

import java.util.List;

@RequiredArgsConstructor
@Service("teamService")
public class TeamService implements ITeamService {
    private final TeamRepository teamRepository;


    @Override
    public List<Team> getAll() {
        return teamRepository.findAll();
    }
}
