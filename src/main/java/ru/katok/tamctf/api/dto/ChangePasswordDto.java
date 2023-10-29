package ru.katok.tamctf.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordDto {
    @NotNull
    @Size(min = 1)
    private String oldPassword;
    @NotNull
    @Size(min = 1)
    private String newPassword;
}
