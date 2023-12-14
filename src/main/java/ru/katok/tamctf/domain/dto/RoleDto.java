package ru.katok.tamctf.domain.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import ru.katok.tamctf.domain.entity.UserEntity;

import java.util.Collection;

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    private Long id;

    private String name;

    @JsonBackReference
    private Collection<UserEntity> users;
}
