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
public class Hint{
    //TODO: стоит ли добавить генерацию айди и если да то как?
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;
    private String task;
    private String taskWrite;
    private String text;
    private String createdBy;
    private String modifiedBy;

}
