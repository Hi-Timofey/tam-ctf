package ru.katok.tamctf.api.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Data
public class UserRestDto {

    @Column(nullable = false)
    private  String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;
}
