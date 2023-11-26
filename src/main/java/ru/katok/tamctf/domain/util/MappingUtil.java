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

    private final TypeMap<UserEntity, UserDto> userDtoMapper = modelMapper.createTypeMap(UserEntity.class, UserDto.class);


    public static UserDto mapToUserDto(UserEntity user) {
        return userDtoMapper.map(user);
    }

    private final TypeMap<SignUpDto, UserEntity> userEntityMapper = modelMapper.createTypeMap(SignUpDto.class, UserEntity.class);

    public static UserEntity mapToUserFromSignUp(SignUpDto request) {
        return userEntityMapper.map(request);
    }

    private final TypeMap<Team, TeamDto> teamDtoMapper = modelMapper.createTypeMap(Team.class, TeamDto.class);

    public static TeamDto mapToTeamDto(Team team) {
        return teamDtoMapper.map(team);
    }

    private final TypeMap<Task, TaskDto> taskDtoMapper = modelMapper.createTypeMap(Task.class, TaskDto.class);

    public static TaskDto mapToTaskDto(Task task) {
        taskDtoMapper.addMappings(mapper -> mapper.map(src -> src.getCategory().getName(), TaskDto::setCategory));
        return taskDtoMapper.map(task);
    }

    private final TypeMap<Hint, HintDto> hintDtoMapper = modelMapper.createTypeMap(Hint.class, HintDto.class);

    public static HintDto mapToHintDto(Hint hint) {
        hintDtoMapper.addMappings(mapper->mapper.map(src->src.getTask().getId(), HintDto::setTaskId));
        return hintDtoMapper.map(hint);
    }

    private final TypeMap<Submission, SubmissionDto> submissionDtoMapper = modelMapper.createTypeMap(Submission.class, SubmissionDto.class);

    public static SubmissionDto mapToSubmissionDto(Submission submission) {
        return submissionDtoMapper.map(submission);
    }
    private final TypeMap<Category,CategoryDto> categoryDtoMapper = modelMapper.createTypeMap(Category.class, CategoryDto.class);
    public static CategoryDto mapToCategoryDto(Category category) {
        return categoryDtoMapper.map(category);
    }

    private TypeMap<File, FileDto> fileDtoMapper = modelMapper.createTypeMap(File.class, FileDto.class);
    public static  FileDto  mapToFileDto(File file){
        return fileDtoMapper.map(file);
    }
}