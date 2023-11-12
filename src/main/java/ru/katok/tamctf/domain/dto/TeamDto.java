package ru.katok.tamctf.domain.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import ru.katok.tamctf.domain.entity.TeamType;

@Data
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TeamDto {

    @NotNull
    @Size(min = 1)
    private String name;

    @NotNull
    private TeamType type;

    @Nullable
    @Size(min = 1)
    private String university;

    @NotNull
    @Size(min = 1)
    private String inviteCode;
}
