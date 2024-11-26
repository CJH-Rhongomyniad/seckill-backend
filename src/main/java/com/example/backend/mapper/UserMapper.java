package com.example.backend.mapper;

import com.example.backend.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findByUsername(String username);
    User findByEmail(String email);
    User findByMobile(String mobile);
    void save(User user);
    void update(User user);

    User login(String username, String password);
}