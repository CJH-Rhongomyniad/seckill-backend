package com.example.backend.service;

import com.example.backend.entity.User;
import com.example.backend.mapper.UserMapper;
import com.example.backend.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public User register(User user) {
        // 检查用户名、邮箱、手机号是否已存在
        if (userMapper.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("用户名已存在");
        }
        if (userMapper.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("邮箱已被注册");
        }
        if (userMapper.findByMobile(user.getMobile()) != null) {
            throw new RuntimeException("手机号已被注册");
        }

        // 生成盐值
        String salt = PasswordUtil.generateSalt();
        user.setSalt(salt);

        // 密码加密
        String encryptedPassword = PasswordUtil.encryptPassword(user.getPassword(), salt);
        user.setPassword(encryptedPassword);

        // 设置注册日期
        user.setRegisterDate(new Date());

        // 保存用户
        userMapper.save(user);
        return user;
    }

    @Override
    public User login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        // 验证密码
        String encryptedPassword = PasswordUtil.encryptPassword(password, user.getSalt());
        if (!encryptedPassword.equals(user.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        // 更新最后登录时间和登录次数
        user.setLastLoginDate(new Date());
        user.setLoginCount(user.getLoginCount() + 1);
        userMapper.update(user);
        return user;
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}