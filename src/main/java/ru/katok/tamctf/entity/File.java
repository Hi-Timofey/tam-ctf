package ru.katok.tamctf.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class File {

    @Id
    private String uuid;

    public File() {
    }
}
