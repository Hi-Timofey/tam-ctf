package ru.katok.tamctf.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
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
    @SequenceGenerator(name = "user_seq",
            sequenceName = "user_sequence",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Long id;

    @Column(unique = true, length = 128)
    private String username;

    private String password;

    private String email;

    @OneToMany(mappedBy = "user", targetEntity = Submission.class)
    private Set<Submission> submissions;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Singular
    private List<RoleEntity> roles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JsonBackReference(value = "user-team")
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
