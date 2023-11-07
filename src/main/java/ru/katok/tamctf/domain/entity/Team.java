package ru.katok.tamctf.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.katok.tamctf.domain.util.GeneratorUtil;

import java.util.Collection;
import java.util.Set;


@Entity
@Data
@Table(name = "teams")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(unique = true, length = 128)
    private String name;

    @Enumerated(EnumType.STRING)
    private TeamType teamType;


    @Column(unique = true, length = 128)
    private String university;

    @Column(unique = true, length = 32)
    @GeneratedValue(generator = GeneratorUtil.randomValueGenerator)
    private String inviteCode;

    @OneToMany(mappedBy = "team")
    private Set<UserEntity> users;

    @OneToMany(mappedBy = "team")
    private Collection<Submission> submissions;

    public void addUserToTeam(UserEntity user) {
        this.users.add(user);
    }
}
