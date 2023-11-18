package ru.katok.tamctf.domain.util;

import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import ru.katok.tamctf.api.dto.SignUpDto;
import ru.katok.tamctf.domain.dto.*;
import ru.katok.tamctf.domain.entity.*;

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

    private TypeMap<Hint, HintDto> hintDtoMapper = modelMapper.createTypeMap(Hint.class, HintDto.class);

    public static HintDto mapToHintDto(Hint hint) {
        return hintDtoMapper.map(hint);
    }

    private TypeMap<Submission, SubmissionDto> submissionDtoMapper = modelMapper.createTypeMap(Submission.class, SubmissionDto.class);

    public static SubmissionDto mapToSubmissionDto(Submission submission) {
        return submissionDtoMapper.map(submission);
    }

}