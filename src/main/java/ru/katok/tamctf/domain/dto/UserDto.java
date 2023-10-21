package ru.katok.tamctf.domain.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import ru.katok.tamctf.domain.entity.RoleEntity;
import ru.katok.tamctf.domain.entity.Team;
import ru.katok.tamctf.domain.entity.UserEntity;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@Builder
@Getter
@AllArgsConstructor
public class UserDto {//implements UserDetails {

    private final String username;
    private final String password;
    private final String email;
    private final List<SimpleGrantedAuthority> authorities;
    private final Set<RoleEntity> roles;
    private final Team team;
    private final boolean isActive;

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