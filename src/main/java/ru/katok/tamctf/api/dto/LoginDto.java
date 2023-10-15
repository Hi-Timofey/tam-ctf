package ru.katok.tamctf.api.dto;


import lombok.Data;

@Data
public class LoginDto {
    private String username;
    private String password;
}
