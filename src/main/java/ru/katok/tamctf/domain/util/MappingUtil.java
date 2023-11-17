package ru.katok.tamctf.domain.util;

import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import ru.katok.tamctf.api.dto.SignUpDto;
import ru.katok.tamctf.domain.dto.TaskDto;
import ru.katok.tamctf.domain.dto.TeamDto;
import ru.katok.tamctf.domain.dto.UserDto;
import ru.katok.tamctf.domain.entity.Task;
import ru.katok.tamctf.domain.entity.Team;
import ru.katok.tamctf.domain.entity.UserEntity;

@UtilityClass
public class MappingUtil {
    private final ModelMapper modelMapper = new ModelMapper();

    private TypeMap<UserEntity, UserDto> userDtoMapper = modelMapper.createTypeMap(UserEntity.class, UserDto.class);


    public static UserDto mapToUserDto(UserEntity user) {
        return userDtoMapper.map(user);
    }

    private TypeMap<SignUpDto, UserEntity> userEntityMapper = modelMapper.createTypeMap(SignUpDto.class, UserEntity.class);

    public static UserEntity mapToUserFromSignUp(SignUpDto request) {
        return userEntityMapper.map(request);
    }

    private TypeMap<Team, TeamDto> teamDtoMapper = modelMapper.createTypeMap(Team.class, TeamDto.class);

    public static TeamDto mapToTeamDto(Team team) {
        return teamDtoMapper.map(team);
    }

    private TypeMap<Task, TaskDto> taskDtoMapper = modelMapper.createTypeMap(Task.class, TaskDto.class);

    public static TaskDto mapToTaskDto(Task task) {
        return taskDtoMapper.map(task);
    }

}