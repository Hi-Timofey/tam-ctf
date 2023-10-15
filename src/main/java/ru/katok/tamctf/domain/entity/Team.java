package ru.katok.tamctf.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Table(name = "File")
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE )
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private  TeamType teamType;

    private String university;
    private String inviteCode;
}
