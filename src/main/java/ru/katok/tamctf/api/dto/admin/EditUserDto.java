package ru.katok.tamctf.api.dto.admin;

import jakarta.annotation.Nullable;
import lombok.Data;
import ru.katok.tamctf.validation.ValidEmail;

@Data
public class EditUserDto {
    @Nullable
    private String username;

    @Nullable
    @ValidEmail
    private String email;

    //TODO: Make Optional (currently null value is FALSE!)
    @Nullable
    private boolean isActive;
}
