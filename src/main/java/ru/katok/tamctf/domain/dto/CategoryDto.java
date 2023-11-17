package ru.katok.tamctf.domain.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import jakarta.persistence.Column;
public class CategoryDto {
    @NotNull
    private String name;
}
