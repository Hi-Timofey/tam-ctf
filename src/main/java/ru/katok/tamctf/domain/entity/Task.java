package ru.katok.tamctf.domain.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "Task")
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String name;
    private String description;//(description != adminDescription)?
    //private String adminDescription;
    @Column(unique = true)
    private String flag;
    private String createdBy;
    private String modifiedBy;
    private boolean active;

    private double scoreInitial;
    private double scoreDelay;
    private double scoreMinimum;

}
