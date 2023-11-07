package ru.katok.tamctf.domain.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import ru.katok.tamctf.domain.entity.TeamType;

@Data
@Builder
@Getter
public class TeamDto {

    @NotNull
    @Size(min = 1)
    private String name;

    @NotNull
    private TeamType type;

    @Nullable
    @Size(min = 1)
    private String university;
}
