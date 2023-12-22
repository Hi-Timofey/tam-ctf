package ru.katok.tamctf.domain.dto;

import lombok.*;

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionDto {
    private Long id;

    private boolean isSuccessful;

    private String flag;

    private String solverIp;

    private Long taskId;

    private Long userId;

}
