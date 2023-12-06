package ru.katok.tamctf.domain.dto;

import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.katok.tamctf.domain.entity.RoleEntity;
import ru.katok.tamctf.domain.entity.Team;

import java.util.List;
import java.util.Set;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {//implements UserDetails {

    private String username;
    /*@JsonIgnore*/
    private String password;
    private String email;
    private List<SimpleGrantedAuthority> authorities;
    private Set<RoleEntity> roles;
    /*@JsonIgnore*/
    private Team team;
    private boolean isActive;

//    @Override
//    public boolean isAccountNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return false;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return false;
//    }

//
//    public static UserDetails fromApplicationUser(UserEntity user, Collection<? extends GrantedAuthority> authorities) {
//        return User.builder()
//                .username(user.getUsername())
//                .password(user.getPassword())
//                .disabled(!user.isActive())
//                .accountExpired(false)
//                .credentialsExpired(false)
//                .accountLocked(false)
//                .authorities(authorities)
//                .build();
//    }
}