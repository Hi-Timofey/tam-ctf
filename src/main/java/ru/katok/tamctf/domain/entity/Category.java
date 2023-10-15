package ru.katok.tamctf.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Setter
@Getter
@Table(name = "Category")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    //TODO: стоит ли добавить генерацию айди и если да то как?
    @Id
    @Column(unique = true)
    private String name; //Название категорий
    private int tasksQuantity; //кол-во тасков
}
