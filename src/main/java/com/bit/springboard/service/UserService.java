package com.bit.springboard.service;

import com.bit.springboard.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User idCheck(String userId);

    User join(User user);

    User login(String userId, String userPw);
}
