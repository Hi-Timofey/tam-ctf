package ru.katok.tamctf.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.katok.tamctf.api.dto.UserRestDto;
import ru.katok.tamctf.domain.dto.UserDto;
import ru.katok.tamctf.domain.entity.Role;
import ru.katok.tamctf.domain.entity.UserEntity;
import ru.katok.tamctf.repository.UserRepository;
import ru.katok.tamctf.domain.error.UserAlreadyExistException;
import ru.katok.tamctf.service.interfaces.IUserService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements IUserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return UserDto.fromApplicationUser(
                userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found!"))
        );
    }

    @Override
    public void deleteUser(final UserEntity user) {
        this.userRepository.delete(user);
    }

    @Override
    public UserEntity registerNewUserAccount(final UserRestDto newUser){
        String username = newUser.getUsername();
        if (this.userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistException(
                    "There is an account with that nickname: " + username);
        }
        UserEntity user = UserEntity.builder()
                .username(username)
                .email(newUser.getEmail())
                .password(newUser.getPassword())
                .build();
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));

        user.setEmail(newUser.getEmail());
        user.setRole(Role.USER);
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
