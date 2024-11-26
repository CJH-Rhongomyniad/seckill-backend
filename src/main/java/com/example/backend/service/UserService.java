package com.example.backend.service;

import com.example.backend.entity.User;

public interface UserService {
    User register(User user);
    User login(String username, String password);
    User findByUsername(String username);
}