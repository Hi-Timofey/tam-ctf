package ru.katok.tamctf.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
