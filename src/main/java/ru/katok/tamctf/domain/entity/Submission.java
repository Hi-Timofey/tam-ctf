package ru.katok.tamctf.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "submissions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "boolean default false")
    private boolean is_successful;

    @Column
    private String solverIp;

    @ManyToOne()
    private Task task;

    @ManyToOne()
    private UserEntity user;

    @ManyToOne()
    private Team team;

}
