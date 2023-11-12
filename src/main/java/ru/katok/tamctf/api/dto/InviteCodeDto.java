package ru.katok.tamctf.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InviteCodeDto {
    @NotNull
    String inviteCode;
}
