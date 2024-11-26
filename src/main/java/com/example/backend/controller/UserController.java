package com.example.backend.controller;

import com.example.backend.config.CommonResult;
import com.example.backend.entity.LoginRequest;
import com.example.backend.entity.User;
import com.example.backend.service.UserService;
import com.example.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/register")
    public CommonResult<?> register(@RequestBody User user) {
        try {
            User registeredUser = userService.register(user);
            logger.info("注册成功");
            return CommonResult.success(registeredUser);
        } catch (Exception e) {
            logger.error("注册失败", e);
            return CommonResult.error((Integer) 400, e.getMessage());
        }
    }

    @PostMapping("/login")
    public CommonResult<?> login(@RequestBody LoginRequest loginRequest) {
        try {
//            String decryptedPassword = RSAUtil.decrypt(loginRequest.getPassword());
            logger.info(loginRequest.getUsername() + " " + loginRequest.getPassword());
            User user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
            // 生成 Token
            String token = JwtUtil.generateToken(user);

            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            data.put("user", user);
            return CommonResult.success(data);
        } catch (Exception e) {
            logger.error("登录失败", e);
            return CommonResult.error((Integer) 400, e.getMessage());
        }
    }

    @GetMapping("/user/info")
    public CommonResult<?> getUserInfo(HttpServletRequest request) {
        try {
            String username = (String) request.getAttribute("username");
            User user = userService.findByUsername(username);
            return CommonResult.success(user);
        } catch (Exception e) {
            logger.error("获取用户信息失败", e);
            return CommonResult.error((Integer) 400, e.getMessage());
        }
//        String username = (String) request.getAttribute("username");
//        User user = userService.findByUsername(username);
//        return CommonResult.success(user);
    }

}