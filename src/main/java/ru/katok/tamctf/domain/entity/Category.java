package ru.katok.tamctf.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Category {

    @Id
    private String name;

    public Category() {}
}
