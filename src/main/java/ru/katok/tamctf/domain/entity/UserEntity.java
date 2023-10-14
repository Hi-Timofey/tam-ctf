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

    @Column(unique = true)
    private String username;

    private String password;

    private String email;



    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;

    private boolean isActive;
    @Column(nullable = true)
    private String team;
    @Column(nullable = true)
    private String atRegisterIp;
    @Column(nullable = true)
    private String lastLoginIp;

}
