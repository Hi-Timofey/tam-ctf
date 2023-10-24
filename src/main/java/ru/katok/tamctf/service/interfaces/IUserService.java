package ru.katok.tamctf.service.interfaces;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.katok.tamctf.api.dto.SignUpDto;
import ru.katok.tamctf.domain.entity.UserEntity;
import ru.katok.tamctf.domain.error.EmailExistsException;

import java.util.List;
import java.util.Optional;

public interface IUserService extends UserDetailsService {
    List<UserEntity> getAll();
    UserEntity registerNewUserAccount(SignUpDto accountDto) throws EmailExistsException;

    void saveRegisteredUser(UserEntity user);

    void deleteUser(UserEntity user);

    Optional<UserEntity> findUserByUsername(String username);

    boolean checkIfValidPassword(UserEntity user, String oldPassword);
    void changeUserPassword(UserEntity user, String password);
}
