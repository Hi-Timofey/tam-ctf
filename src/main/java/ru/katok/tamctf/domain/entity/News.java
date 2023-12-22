package ru.katok.tamctf.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Setter
@Getter
@Table(name = "news")
@NoArgsConstructor
@AllArgsConstructor
public class News {

    @Id
    @SequenceGenerator(name = "news_seq",
            sequenceName = "news_sequence",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "news_seq")
    private Long id;
    @Enumerated(EnumType.STRING)
    private NewsType itemType;

    private String relatedTask;
    private String relatedHint;
    @Column()
    private String text;
}
