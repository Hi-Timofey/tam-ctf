package ru.katok.tamctf.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import ru.katok.tamctf.domain.util.GeneratorUtil;

import java.util.Collection;
import java.util.HashSet;
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
    @JsonManagedReference
    private Set<UserEntity> users;

    @OneToMany(mappedBy = "team")
    private Collection<Submission> submissions;

}
