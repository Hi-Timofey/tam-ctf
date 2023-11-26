package ru.katok.tamctf.domain.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
@Data
@Builder
@Getter
@AllArgsConstructor
public class CategoryDto {
    @NotNull
    private String name;
}
