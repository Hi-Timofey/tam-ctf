package ru.katok.tamctf.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "tasks")
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true, length = 64)
    private String name;

    @Column()
    private String description;
    @Column()
    private String adminDescription;

    @Column(length = 64)
    private String flag;
    private String createdBy;
    private String modifiedBy;
    @Column(columnDefinition = "boolean default false")
    private boolean active;

    @OneToMany(mappedBy = "task")
    private Set<Submission> submissions;

    @OneToMany(mappedBy = "task")
    private Collection<Hint> hints;

    @OneToOne(optional = false)
    private Category category;

    @OneToMany(mappedBy = "task")
    private Collection<File> files;

    private double scoreInitial;
    private double scoreDelay;
    private double scoreMinimum;
}