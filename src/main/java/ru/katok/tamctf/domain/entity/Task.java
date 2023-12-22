package ru.katok.tamctf.domain.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "tasks")
@AllArgsConstructor
@Builder
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

    @Column(columnDefinition = "boolean default false")
    private boolean active;

    @Enumerated(EnumType.STRING)
    private TaskDifficulty taskDifficulty;

    @Column()
    private String author;

    @OneToMany(mappedBy = "task")
    @JsonManagedReference(value = "task-submission")
    private Set<Submission> submissions;

    @OneToMany(mappedBy = "task")
    @JsonManagedReference(value = "task-hint")
    private Collection<Hint> hints;

    @ManyToOne(optional = false)
    private Category category;

    @OneToMany(mappedBy = "task")
    private Collection<File> files;

    private int scoreInitial;
    private int scoreDelay;
    private int scoreMinimum;
}
