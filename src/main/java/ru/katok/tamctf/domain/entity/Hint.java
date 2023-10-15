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
    //TODO: стоит ли добавить генерацию айди и если да то как?
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String text;

    @ManyToOne()
    private Task task;

}
