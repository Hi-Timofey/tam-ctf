package ru.katok.tamctf.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import ru.katok.tamctf.domain.entity.Task;
@Data
@Builder
@Getter
@AllArgsConstructor
public class FileDto {



    private Task task;
}
