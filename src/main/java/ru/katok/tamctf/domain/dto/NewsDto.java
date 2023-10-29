package ru.katok.tamctf.domain.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import ru.katok.tamctf.domain.entity.NewsType;

public class NewsDto {
    private NewsType NewsType;

    private String relatedTask;
    private String relatedHint;
    @Column()
    private String text;
}
