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

    public Task(String name, String description, String adminDescription, String flag, boolean active, double scoreInitial, double scoreDellay, double scoreMinimum) {
        this.name = name;
        this.description = description;
        this.adminDescription = adminDescription;
        this.flag = flag;
        this.active = active;
        this.scoreInitial = scoreInitial;
        this.scoreDelay = scoreDellay;
        this.scoreMinimum = scoreMinimum;
    }

    public String toString() {
        return String.format("<Task id=%d name=%s>", id, name);
    }

    public void setName(String name) {
        this.name = name;
    }

}
