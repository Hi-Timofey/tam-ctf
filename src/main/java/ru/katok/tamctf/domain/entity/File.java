package ru.katok.tamctf.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Table(name = "file")
@NoArgsConstructor
@AllArgsConstructor
public class File {

    //TODO: Придумать что делать с файлом
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @ManyToOne(optional = false)
    private Task task;

}


