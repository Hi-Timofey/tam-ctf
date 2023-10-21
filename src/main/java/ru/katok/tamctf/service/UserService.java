package ru.katok.tamctf.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.katok.tamctf.api.dto.SignUpDto;
import ru.katok.tamctf.domain.dto.UserDto;
import ru.katok.tamctf.domain.entity.Permission;
import ru.katok.tamctf.domain.entity.RoleEntity;
import ru.katok.tamctf.domain.entity.UserEntity;
import ru.katok.tamctf.domain.error.EmailExistsException;
import ru.katok.tamctf.domain.error.UserAlreadyExistException;
import ru.katok.tamctf.repository.RoleRepository;
import ru.katok.tamctf.repository.UserRepository;
import ru.katok.tamctf.service.interfaces.IUserService;

import java.util.*;

@SuppressWarnings("ALL")
@Service("userDetailsService")
@RequiredArgsConstructor
@Transactional
public class UserService implements IUserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
//        return UserDto.fromApplicationUser(user, getAuthorities(user.getRoles()));
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .disabled(!user.isActive())
                .accountExpired(false)
                .credentialsExpired(false)
                .accountLocked(false)
                .authorities(getAuthorities(user.getRoles()))
                .build();
    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<RoleEntity> roles) {

        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<RoleEntity> roles) {

        List<String> privileges = new ArrayList<>();
        List<Permission> collection = new ArrayList<>();
        for (RoleEntity role : roles) {
            privileges.add(role.getName());
            collection.addAll(role.getPermissions());
        }
        for (Permission item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    @Override
    public void saveRegisteredUser(final UserEntity user){
        userRepository.save(user);
    }

    @Override
    public void deleteUser(final UserEntity user) {
        this.userRepository.delete(user);
    }

    @Override
    public Optional<UserEntity> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserEntity registerNewUserAccount(final SignUpDto newUser) throws EmailExistsException{
        String username = newUser.getUsername();
        if (this.userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistException(
                    "There is an account with that nickname: " + username);
        }
        if (this.userRepository.existsByEmail(newUser.getEmail())){
            throw new EmailExistsException("There is an account with that email: " + username);

        }

        UserEntity user = UserEntity.builder()
                .username(username)
                .email(newUser.getEmail())
                .password(newUser.getPassword())
                .roles(Collections.singleton(roleRepository.findByName("ROLE_USER")))
                .build();
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));

        user.setEmail(newUser.getEmail());
//        user.setRoles();
        return this.userRepository.save(user);
    }

    private boolean emailExists(final String email) {
        return this.userRepository.findByEmail(email).isPresent();
    }


    public List<UserEntity> getAll() {
        return this.userRepository.findAll();
    }

    public UserEntity save(UserEntity newUser) {
        return this.userRepository.save(newUser);
    }
}
