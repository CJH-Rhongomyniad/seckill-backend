package com.example.backend.service;

import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User registerUser(String username, String password, String email) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username is already taken.");
        }

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email is already in use.");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        if (userRepository.save(user)) {
            return user;
        } else {
            throw new RuntimeException("Failed to save user.");
        }
    }

    public User loginUser(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found."));

        if (!password.equals(user.getPassword())) {
            throw new RuntimeException("Invalid credentials.");
        }

        return user;
    }
}
