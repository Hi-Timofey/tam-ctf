package ru.katok.tamctf.domain.dto;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    @NotNull
        private String name;
}
