package ru.katok.tamctf.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Table(name = "users")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity extends TimeStampMixin {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, length = 128)
    private String username;

    private String password;

    private String email;

    @OneToMany(mappedBy = "user")
    private Set<Submission> submissions;


    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;


    @Column(columnDefinition = "boolean default false")
    private boolean isActive;

    @Column()
    private String atRegisterIp;

    @Column()
    private String lastLoginIp;

    @ManyToOne()
    private Team team;
}
