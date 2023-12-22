package ru.katok.tamctf.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.katok.tamctf.validation.PasswordPolicy;

@Data
public class ChangePasswordDto {
    @NotNull
    @Size(min = 1)
    private String oldPassword;

    @NotNull
    @Size(min = 1)
    @PasswordPolicy
    private String newPassword;
}
