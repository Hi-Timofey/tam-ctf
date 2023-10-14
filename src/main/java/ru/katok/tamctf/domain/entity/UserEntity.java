package ru.katok.tamctf.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "users")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String username;
    private String password;

    private String email;



    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;

    private boolean isActive;
    private String team;
    private String atRegisterIp;
    private String lastLoginIp;

}
