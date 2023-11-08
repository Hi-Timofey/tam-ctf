package ru.katok.tamctf.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity extends TimeStampMixin {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, length = 128)
    private String username;

    @JsonIgnore
    private String password;

    private String email;

    @OneToMany(mappedBy = "user", targetEntity = Submission.class)
    private Set<Submission> submissions;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonManagedReference
    @Singular
    private Set<RoleEntity> roles;

    @ManyToOne()
    @JoinColumn(name = "team_id")
    @JsonBackReference
    private Team team;

    @Column(columnDefinition = "boolean default false")
    private boolean active;

    @Column()
    private String atRegisterIp;

    @Column()
    private String lastLoginIp;


    public void addRole(RoleEntity role) {
        this.roles.add(role);
    }
}
