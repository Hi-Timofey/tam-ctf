package ru.katok.tamctf.api.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.katok.tamctf.validation.PasswordMatches;
import ru.katok.tamctf.validation.ValidEmail;

@Data
@PasswordMatches
public class SignUpDto {

    @NotNull
    @Size(min = 1)
    private String username;

    @NotNull
    @Size(min = 1)
    private String password;

    @NotNull
    @Size(min = 1)
    private String matchingPassword;

    @ValidEmail
    @NotNull
    @Size(min = 3)
    private String email;
}
