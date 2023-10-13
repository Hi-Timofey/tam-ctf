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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public TeamType getTeamType() {
        return teamType;
    }

    public void setTeamType(TeamType teamType) {
        this.teamType = teamType;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }
}
