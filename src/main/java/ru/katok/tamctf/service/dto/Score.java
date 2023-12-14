package ru.katok.tamctf.service.dto;

import lombok.*;
import ru.katok.tamctf.domain.dto.TeamDto;
import ru.katok.tamctf.domain.entity.TeamType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Score {
    private String teamName;
    private int score;
    private TeamType teamType;
}
