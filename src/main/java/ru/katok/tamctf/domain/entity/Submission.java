package ru.katok.tamctf.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "Submissions")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Submission extends TimeStampMixin {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;

    private String user;
    private String task;
    private boolean is_successful;
    private int solverIp;

}
