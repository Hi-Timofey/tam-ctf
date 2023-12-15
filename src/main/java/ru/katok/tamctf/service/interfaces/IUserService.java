package ru.katok.tamctf.service.interfaces;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.katok.tamctf.api.dto.SignUpDto;
import ru.katok.tamctf.domain.dto.UserDto;
import ru.katok.tamctf.domain.error.EmailExistsException;
import ru.katok.tamctf.domain.error.UserNotFoundException;

import java.util.List;

public interface IUserService extends UserDetailsService {
    List<UserDto> getAll();

    UserDto registerNewUserAccount(SignUpDto accountDto) throws EmailExistsException;

    UserDto createNewUserAccount(UserDto userDto);

    void deleteUserById(Long id);

    UserDto findUserByUsername(String username) throws UserNotFoundException;

    UserDto getUserById(Long id) throws UserNotFoundException;

    boolean recoverUser(String email);

    boolean changeUserPassword(String oldPassword, String newPassword);
}
