package com.example.backend.controller;

import com.example.backend.config.CommonResult;
import com.example.backend.model.User;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public CommonResult<?> register(@RequestBody User userDTO) {
        try {
            User user = userService.registerUser(userDTO.getUsername(), userDTO.getPassword(), userDTO.getEmail());
            return CommonResult.success("User registered successfully.");
        } catch (RuntimeException e) {
            return CommonResult.error((Integer) 400, e.getMessage());
        }
    }

    @PostMapping("/login")
    public CommonResult<?> login(@RequestBody User userDTO) {
        try {
            User user = userService.loginUser(userDTO.getUsername(), userDTO.getPassword());
            return CommonResult.success("User logged in successfully.");
        } catch (RuntimeException e) {
            return CommonResult.error((Integer) 401, e.getMessage());
        }
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        try {
            return ResponseEntity.ok("User logged in successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}

