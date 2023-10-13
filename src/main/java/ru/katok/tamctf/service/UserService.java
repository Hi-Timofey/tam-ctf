package ru.katok.tamctf.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.katok.tamctf.domain.dto.UserDto;
import ru.katok.tamctf.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return UserDto.fromApplicationUser(
                userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found!"))
        );
    }
}
