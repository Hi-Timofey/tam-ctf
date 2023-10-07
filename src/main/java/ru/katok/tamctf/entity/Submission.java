package ru.katok.tamctf.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Submission {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;

    public Submission() {}
}
