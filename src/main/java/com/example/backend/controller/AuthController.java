package com.example.backend.controller;

import com.example.backend.config.CommonResult;
import com.example.backend.model.TokenResponse;
import com.example.backend.model.User;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.backend.utils.JwtTokenUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

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
            System.out.println(userDTO.getUsername());
            User user = userService.loginUser(userDTO.getUsername(), userDTO.getPassword());
            if (user == null) {
                return CommonResult.error((Integer) 401, "Invalid credentials.");
            }

            String username = user.getUsername();

            // 生成访问令牌和刷新令牌
            String accessToken = jwtTokenUtil.generateAccessToken(username);
            String refreshToken = jwtTokenUtil.generateRefreshToken(username);
            TokenResponse token_resp = new TokenResponse(accessToken,refreshToken);

            CommonResult<TokenResponse> result = CommonResult.success(token_resp);

            return result;
        } catch (RuntimeException e) {
            return CommonResult.error((Integer) 401, e.getMessage());
        }
    }

    @GetMapping("/profile/get")
    public CommonResult<?> getUserProfile(@RequestHeader("Authorization") String authHeader) {
        System.out.println(authHeader);
        // 解析Authorization请求头中的JWT令牌 Bearer access_token
        String token = authHeader.substring(7);
        System.out.println(token);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        if (username == null) {
            return CommonResult.error((Integer) 401, "Invalid token.");
        }

        User foundUser = userService.findByUsername(username);

        CommonResult<User> result = CommonResult.success(foundUser);
        return result;
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

