package ru.katok.tamctf.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class User {// implements UserDetails {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE )
    private Long id;

    private String username;

    private String email;

    private boolean isAdmin;
    private boolean isActive;
    private boolean isTeamCapitan;

    private String telegram;

    public String toString() {
        return String.format("<User id=%d username=%s>", id, username);
    }

    public User(){    }
}
