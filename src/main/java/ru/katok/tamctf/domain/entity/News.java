package ru.katok.tamctf.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Table(name = "News")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class News extends TimeStampMixin {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;
    private String itemType;
    private String relatedTask;
    private String relatedHint;
    private String text;
    private int createdAt;
    private int modifiedAt;
}
