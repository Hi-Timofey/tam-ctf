package ru.katok.tamctf.domain.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {


    @NotNull
    private Long id;
    @NotNull
    @Size(min = 1)
    private String name;
    @Nullable
    @Size(min = 1)
    private String description;
    @Nullable
    @Size(min = 1)
    private String adminDescription;
    private boolean active;
    @NotNull
    @Size(min = 1)
    private String flag;
    @Nullable
    private int scoreInitial;
    @Nullable
    private int scoreDelay;
    @Nullable
    private int scoreMinimum;

    @NotNull
    @Size(min = 1)
    private String category;
}
