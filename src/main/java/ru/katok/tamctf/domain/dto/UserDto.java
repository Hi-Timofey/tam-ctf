package ru.katok.tamctf.domain.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.annotation.Nullable;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.katok.tamctf.domain.entity.RoleEntity;

import java.util.List;
import java.util.Set;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String username;
    @JsonIgnore
    private String password;
    private String email;
    @JsonIgnore
    private List<SimpleGrantedAuthority> authorities;

    @JsonManagedReference
    private Set<RoleEntity> roles;

    @Nullable
    private TeamDto team;
    private boolean isActive;
}