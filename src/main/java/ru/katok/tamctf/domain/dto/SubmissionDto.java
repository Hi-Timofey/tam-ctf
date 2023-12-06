package ru.katok.tamctf.domain.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import ru.katok.tamctf.domain.entity.Task;
import ru.katok.tamctf.domain.entity.Team;
import ru.katok.tamctf.domain.entity.UserEntity;

@Data
@Builder
@Getter
@AllArgsConstructor
public class SubmissionDto {

    private boolean isSuccessful;

    private String flag;

    private String solverIp;

    private Long taskId;

    private Long userId;

    private Long teamId;
}
