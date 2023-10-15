package ru.katok.tamctf.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "File")
@NoArgsConstructor
@AllArgsConstructor
public class File {

    //TODO: Придумать что делать с файлом
    @Id
    private String uuid;
}


