package ru.katok.tamctf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.katok.tamctf.domain.entity.Permission;
import ru.katok.tamctf.domain.entity.RoleEntity;
import ru.katok.tamctf.domain.entity.UserEntity;
import ru.katok.tamctf.repository.PermissionRepository;
import ru.katok.tamctf.repository.RoleRepository;
import ru.katok.tamctf.repository.UserRepository;

import java.util.*;

@SuppressWarnings("ALL")
@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PermissionRepository permissionRepository;
    boolean alreadySetup = false;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;

        //TODO: Tooo big rewrite with no existence check
        // == create initial privileges
        final Permission read = createPrivilegeIfNotFound("READ_PRIVILEGE");
        final Permission modify = createPrivilegeIfNotFound("MODIFICATION_PRIVILEGE");

        // == create initial roles
        final List<Permission> adminPrivileges = new ArrayList<>(Arrays.asList(read, modify));
        final List<Permission> userPrivileges = new ArrayList<>(List.of(read));
        final RoleEntity adminRole = createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", userPrivileges);

        // == create initial user
        createUserIfNotFound("admin", "admin", Collections.singleton(adminRole));

        alreadySetup = true;
    }

    private Permission createPrivilegeIfNotFound(String name) {

        Permission privilege = permissionRepository.findByName(name);
        if (privilege == null) {
            privilege = new Permission(name);
            permissionRepository.save(privilege);
        }
        return privilege;
    }

    private RoleEntity createRoleIfNotFound(
            String name, Collection<Permission> privileges) {

        RoleEntity role = roleRepository.findByName(name);
        if (role == null) {
            role = new RoleEntity(name);
            role.setPermissions(privileges);
            roleRepository.save(role);
        }
        return role;
    }


    private UserEntity createUserIfNotFound(final String username, final String password, final Set<RoleEntity> roles) {
        UserEntity user = userRepository.findByUsernameAndActive(username, true);
        if (user == null) {
            user = new UserEntity();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setActive(true);
            user.setRoles(roles);
            user = userRepository.save(user);
        }
        return user;
    }
}
