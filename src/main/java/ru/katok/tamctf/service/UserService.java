package ru.katok.tamctf.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
import ru.katok.tamctf.domain.entity.Team;
import ru.katok.tamctf.domain.entity.UserEntity;
import ru.katok.tamctf.domain.error.EmailExistsException;
import ru.katok.tamctf.domain.error.UserAlreadyExistException;
import ru.katok.tamctf.domain.error.UserNotFoundException;
import ru.katok.tamctf.domain.util.MappingUtil;
import ru.katok.tamctf.repository.RoleRepository;
import ru.katok.tamctf.repository.TeamRepository;
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
    private final PasswordEncoder passwordEncoder;
    private final TeamRepository teamRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        return User.builder().username(user.getUsername()).password(user.getPassword()).disabled(!user.isActive()).accountExpired(false).credentialsExpired(false).accountLocked(false).authorities(getAuthorities(user.getRoles())).build();
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<RoleEntity> roles) {

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
    public void deleteUserById(Long id) {
        userRepository.delete(userRepository.getById(id));
    }

    @Override
    public UserDto findUserByUsername(String username) throws UserNotFoundException {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() ->
                new UserNotFoundException("no such user with username: {}".format(username)));
        return MappingUtil.mapToUserDto(user);
    }

    private boolean checkIfValidPassword(UserEntity user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    private void changeUserPassword(UserEntity user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public UserDto registerNewUserAccount(final SignUpDto newUser) throws EmailExistsException {
        String username = newUser.getUsername();
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistException("There is an account with that nickname: " + username);
        }
        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new EmailExistsException("There is an account with that email: " + newUser.getEmail());

        }
        UserEntity user = MappingUtil.mapToUserFromSignUp(newUser);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(roleRepository.findByName("ROLE_USER")));
        user.setActive(true);
        return MappingUtil.mapToUserDto(userRepository.save(user));
    }

    public UserDto createNewUserAccount(UserDto newUser) {
        if (userRepository.findByUsername(newUser.getUsername()).isPresent()) {
            throw new UserAlreadyExistException("A user with that name already exists.");
        }
        Optional<Team> optionalTeam = teamRepository.findByName(newUser.getTeam().getName());
        Team team = null;
        if (!optionalTeam.isEmpty() && optionalTeam.isPresent()) {
            team = optionalTeam.get();
        }
        /*Optional<RoleEntity> optionalRole = roleRepository.findByName();*/
        //TODO: ИСПРАВИТЬ
        UserEntity user = UserEntity.builder()
                .username(newUser.getUsername())
                .password(newUser.getPassword())
                .email(newUser.getEmail())
                .active(newUser.isActive())
                .roles(newUser.getRoles()) //USER_ROLE only((
                .team(team) // NULL
                .build();
        return MappingUtil.mapToUserDto(userRepository.save(user));
    }

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(MappingUtil::mapToUserDto).toList();
    }

    @Override
    public UserDto getUserById(Long id) throws UserNotFoundException {
        return MappingUtil.mapToUserDto(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("no such user with id: " + Long.toString(id))));
    }

    @Override
    public boolean recoverUser(String email) {
        return false;
    }

    @Override
    public boolean changeUserPassword(String oldPassword, String newPassword) {
        return false;
    }
}
