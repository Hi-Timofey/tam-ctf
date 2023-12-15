package ru.katok.tamctf.service.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PublicTaskDto {

    @NotNull
    private Long id;

    @NotNull
    @Size(min = 1)
    private String name;
    @Nullable
    @Size(min = 1)
    private String description;
    @NotNull
    @Size(min = 1)
    private String category;

    private int solves;
    @Nullable
    private Integer score;
}