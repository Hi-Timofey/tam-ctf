package ru.katok.tamctf.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdminDescription() {
        return adminDescription;
    }

    public void setAdminDescription(String adminDescription) {
        this.adminDescription = adminDescription;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double getScoreInitial() {
        return scoreInitial;
    }

    public void setScoreInitial(double scoreInitial) {
        this.scoreInitial = scoreInitial;
    }

    public double getScoreDelay() {
        return scoreDelay;
    }

    public void setScoreDelay(double scoreDelay) {
        this.scoreDelay = scoreDelay;
    }

    public double getScoreMinimum() {
        return scoreMinimum;
    }

    public void setScoreMinimum(double scoreMinimum) {
        this.scoreMinimum = scoreMinimum;
    }
}
