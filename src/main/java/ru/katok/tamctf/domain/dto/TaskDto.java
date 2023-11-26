package ru.katok.tamctf.domain.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
@AllArgsConstructor
public class TaskDto {
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
    private double scoreInitial;
    @Nullable
    private double scoreDelay;
    @Nullable
    private double scoreMinimum;
    @NotNull
    @Size(min = 1)
    private String category;
}
