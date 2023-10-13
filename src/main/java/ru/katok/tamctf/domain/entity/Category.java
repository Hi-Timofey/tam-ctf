package ru.katok.tamctf.domain.entity;

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
    @Id
    private String name;

}
