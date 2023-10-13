package ru.katok.tamctf.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Entity
@Data
@Table(name = "File")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class File {

    //TODO: Придумать что делать с файлом
    @Id
    private String uuid;

}
