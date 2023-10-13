package ru.katok.tamctf.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Entity
@Data
@Table(name = "TimeStampMixin")
@NoArgsConstructor
@AllArgsConstructor
public class TimeStampMixin {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant modifiedAt;
}
