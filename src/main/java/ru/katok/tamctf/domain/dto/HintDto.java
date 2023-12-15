package ru.katok.tamctf.domain.dto;

import lombok.*;

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HintDto {

    private String text;

    private Long taskId;

}