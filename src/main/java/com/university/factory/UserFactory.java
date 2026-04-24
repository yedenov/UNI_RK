package com.university.factory;

import com.university.model.Role;
import com.university.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {

    private final PasswordEncoder passwordEncoder;

    public UserFactory(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(String username, String email,
                           String password, String firstName,
                           String lastName, Role role) {
        return switch (role) {
            case STUDENT -> buildUser(username, email, password, firstName, lastName, Role.STUDENT);
            case TEACHER -> buildUser(username, email, password, firstName, lastName, Role.TEACHER);
            case ADMIN   -> buildUser(username, email, password, firstName, lastName, Role.ADMIN);
        };
    }

    private User buildUser(String username, String email, String password,
                           String firstName, String lastName, Role role) {
        return User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .firstName(firstName)
                .lastName(lastName)
                .role(role)
                .enabled(true)
                .build();
    }
}