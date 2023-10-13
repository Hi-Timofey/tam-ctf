package ru.katok.tamctf.domain.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "Task")
@AllArgsConstructor
@Builder
public class Task extends Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Getter
    private String name;
    private String description;
    private String adminDescription;
    private String flag;
    private boolean active;

    private double scoreInitial;
    private double scoreDelay;
    private double scoreMinimum;

}
