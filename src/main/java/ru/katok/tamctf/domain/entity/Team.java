package ru.katok.tamctf.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @SequenceGenerator(name = "team_seq",
            sequenceName = "team_sequence",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_seq")
    private Long id;
    @Column(unique = true, length = 128)
    private String name;

    @Enumerated(EnumType.STRING)
    private TeamType teamType;


    @Column(length = 128)
    private String university;

    //TODO private String inviteCode = GeneratorUtil.generateCleanUuid();
    @Column(unique = true)
    private String inviteCode;

    @OneToMany(
            mappedBy = "team",
            fetch = FetchType.LAZY,
            targetEntity = UserEntity.class,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    @JsonManagedReference(value = "user-team")
    private Set<UserEntity> users;


}
