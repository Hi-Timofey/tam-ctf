package ru.katok.tamctf.domain.util;

import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;
import ru.katok.tamctf.api.dto.SignUpDto;
import ru.katok.tamctf.domain.dto.TaskDto;
import ru.katok.tamctf.domain.dto.TeamDto;
import ru.katok.tamctf.domain.dto.UserDto;
import ru.katok.tamctf.domain.entity.Task;
import ru.katok.tamctf.domain.entity.Team;
import ru.katok.tamctf.domain.entity.UserEntity;

@UtilityClass
public class MappingUtil {
    ModelMapper modelMapper = new ModelMapper();


    public static UserDto mapToUserDto(UserEntity user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }

    /*
    public static UserDto mapToUserDto(UserEntity user) {
        return UserDto.builder().username(user.getUsername()).email(user.getEmail()).roles(user.getRoles()).team(user.getTeam()).build();
    }
    */

    public static UserEntity mapToUserFromSignUp(SignUpDto request) {
        UserEntity userEntity = modelMapper.map(request, UserEntity.class);
        return userEntity;
    }

/*
    public static UserEntity mapToUserFromSignUp(SignUpDto request) {
        return UserEntity.builder().username(request.getUsername()).password(request.getPassword()).email(request.getEmail()).build();
    }
*/

    public static TeamDto mapToTeamDto(Team team) {
      TeamDto teamDto = modelMapper.map(team, TeamDto.class);
      return teamDto;
    }

    /*public static TeamDto mapToTeamDto(Team team) {
        return TeamDto.builder()
                .name(team.getName())
                .type(team.getTeamType())
                .inviteCode(team.getInviteCode())
                .university(team.getUniversity())
                .build();
    }*/
    public static TaskDto mapToTaskDto(Task task) {
        TaskDto taskDto = modelMapper.map(task, TaskDto.class);
        return taskDto;
    }

    /*public static TaskDto mapToTaskDto(Task task) {
        return TaskDto.builder()
                .name(task.getName())
                .description(task.getDescription())
                .build();
    }*/
}