package ru.katok.tamctf.service.interfaces;

import ru.katok.tamctf.domain.entity.Team;

import java.util.List;

public interface ITeamService {
    List<Team> getAll();
}
