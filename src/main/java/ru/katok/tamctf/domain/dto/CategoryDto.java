package ru.katok.tamctf.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import jakarta.persistence.Column;
public class CategoryDto {
    @Column(unique = true, length=16)
    private String name;
}
