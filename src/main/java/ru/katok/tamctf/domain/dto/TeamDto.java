package ru.katok.tamctf.domain.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import ru.katok.tamctf.domain.entity.TeamType;

@Data
@Builder
@Getter
@AllArgsConstructor
public class TeamDto {

    private String name;

    private TeamType teamType;

    private String university;
}
