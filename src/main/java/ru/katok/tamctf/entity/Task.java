package ru.katok.tamctf.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;


    private String name;
    private String description;
    private String adminDescription;
    private String flag;
    private boolean active;

    private double scoreInitial;
    private double scoreDelay;
    private double scoreMinimum;

    protected Task() {}

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
}
