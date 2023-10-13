package ru.katok.tamctf.domain.entity;

import jakarta.persistence.*;


@Entity
public class Team {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE )
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private  TeamType teamType;

    private String university;
    private String inviteCode;

    public Team() {
    }

    public Team(String name, TeamType teamType, String university) {
        this.name = name;
        this.teamType = teamType;
        this.university = university;
        this.inviteCode = "GENERATED_UNIQ_FIELD";
    }

    public String toString() {
        return String.format("<Team id=%d name=%s>", id, name);
    }
}
