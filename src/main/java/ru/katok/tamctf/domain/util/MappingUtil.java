package ru.katok.tamctf.domain.util;

import lombok.experimental.UtilityClass;
import ru.katok.tamctf.api.dto.SignUpDto;
import ru.katok.tamctf.domain.dto.TeamDto;
import ru.katok.tamctf.domain.dto.UserDto;
import ru.katok.tamctf.domain.entity.Team;
import ru.katok.tamctf.domain.entity.UserEntity;

@UtilityClass
public class MappingUtil {

    public static UserDto mapToUserDto(UserEntity user) {
        return UserDto.builder().username(user.getUsername()).email(user.getEmail()).roles(user.getRoles()).team(user.getTeam()).build();
    }

    public static UserEntity mapToUserFromSignUp(SignUpDto request) {
        return UserEntity.builder().username(request.getUsername()).password(request.getPassword()).email(request.getEmail()).build();
    }

    public static TeamDto mapToTeamDto(Team team) {
        return TeamDto.builder()
                .name(team.getName())
                .type(team.getTeamType())
                .university(team.getUniversity())
                .build();
    }

}