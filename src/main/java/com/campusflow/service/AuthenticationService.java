package com.campusflow.service;

import com.campusflow.model.User;
import com.campusflow.repository.UserRepository;
import com.campusflow.security.Role;
import com.campusflow.util.PasswordUtil;
import com.campusflow.exception.AuthenticationException;

public class AuthenticationService {

    private final UserRepository userRepository;

    public AuthenticationService() {
        this.userRepository = new UserRepository();
    }

    // -----------------------------
    // Login
    // -----------------------------
    public User login(String email, String password) {

        validateLoginInput(email, password);

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new AuthenticationException(
                    "Invalid email or password."
            );
        }

        if (!PasswordUtil.verifyPassword(
                password,
                user.getPassword())) {

            throw new AuthenticationException(
                    "Invalid email or password."
            );
        }

        return user;
    }

    // -----------------------------
    // Get User By ID
    // -----------------------------
    public User getUserById(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Invalid user ID."
            );
        }

        return userRepository.findById(id);
    }

    // -----------------------------
    // Role Check
    // -----------------------------
    public boolean hasRole(User user, String role) {

        if (user == null) {
            return false;
        }

        if (role == null || role.isBlank()) {
            return false;
        }

        return user.getRole().equalsIgnoreCase(role);
    }

    // -----------------------------
    // Input Validation
    // -----------------------------
    private void validateLoginInput(
            String email,
            String password) {

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException(
                    "Email is required."
            );
        }

        if (!email.contains("@")) {
            throw new IllegalArgumentException(
                    "Enter a valid email address."
            );
        }

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException(
                    "Password is required."
            );
        }
    }
}