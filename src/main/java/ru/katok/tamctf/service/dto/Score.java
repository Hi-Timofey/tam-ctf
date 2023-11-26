package ru.katok.tamctf.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import ru.katok.tamctf.domain.dto.TeamDto;
import ru.katok.tamctf.domain.entity.TeamType;

@Data
@Builder
@AllArgsConstructor
public class Score {
    private String teamName;
    private int score;
    private TeamType teamType;
}
