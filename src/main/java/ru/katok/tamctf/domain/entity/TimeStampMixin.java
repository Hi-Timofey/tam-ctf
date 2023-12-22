package ru.katok.tamctf.domain.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
@MappedSuperclass
public abstract class TimeStampMixin {
    @Column(name = "created_at")
    @CreationTimestamp
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;
}
