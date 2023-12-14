package ru.katok.tamctf.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SolveDto {

    @NotNull
    @Size(min = 2)
    private String flag;

    @NotNull
    private Long taskId;
}
