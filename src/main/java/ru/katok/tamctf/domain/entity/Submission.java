package ru.katok.tamctf.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "submissions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Submission {
    @Id
    @SequenceGenerator(name = "sub_seq",
            sequenceName = "sub_sequence",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sub_seq")
    private Long id;

    @Column(columnDefinition = "boolean default false")
    private boolean isSuccessful;

    @Column
    private String flag;

    @Column
    private String solverIp;

    @ManyToOne()
    private Task task;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
