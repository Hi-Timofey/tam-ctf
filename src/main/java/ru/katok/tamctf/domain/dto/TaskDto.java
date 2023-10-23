package ru.katok.tamctf.domain.dto;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import ru.katok.tamctf.domain.entity.Category;
import ru.katok.tamctf.domain.entity.File;
import ru.katok.tamctf.domain.entity.Hint;
import ru.katok.tamctf.domain.entity.Submission;

import java.util.Collection;
import java.util.Set;

@Data
@Builder
@Getter
@AllArgsConstructor
public class TaskDto {

    private String name;

    private String description;
}
