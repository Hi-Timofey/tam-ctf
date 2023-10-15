package ru.katok.tamctf.service.interfaces;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.katok.tamctf.api.dto.UserRestDto;
import ru.katok.tamctf.domain.entity.UserEntity;

import java.util.List;

public interface IUserService extends UserDetailsService {
    List<UserEntity> getAll();
    UserEntity registerNewUserAccount(UserRestDto accountDto) throws Exception;

    void deleteUser(UserEntity user);


}
