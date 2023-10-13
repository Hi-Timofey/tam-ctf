package ru.katok.tamctf.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Set;

import java.util.Collection;

@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO )
    private Long id;

    private String username;
    private String password;

    private String email;

    private boolean isActive;

    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "users_roles",
//            joinColumns = @JoinColumn(
//                    name = "user_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(
//                    name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;
    private String telegram;

    public String toString() {
        return String.format("<User id=%d username=%s>", id, username);
    }

    public User(){    }

    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
