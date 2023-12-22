package ru.katok.tamctf.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Table(name = "Hint")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hint extends TimeStampMixin {
    @Id
    @SequenceGenerator(name = "hint_seq",
            sequenceName = "hint_sequence",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hint_seq")
    private Long id;
    private String text;

    @ManyToOne()
    private Task task;

}
