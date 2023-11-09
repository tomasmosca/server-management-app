package com.app.server.service;

import com.app.server.model.Role;
import com.app.server.model.User;
import com.app.server.repository.RoleRepository;
import com.app.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName(Role.RoleName.valueOf("ROLE_USER"))
                .orElseThrow(() -> new EntityNotFoundException("User Role not set."));
        user.setRoles(Collections.singleton(userRole));
        return userRepository.save(user);
    }

}
