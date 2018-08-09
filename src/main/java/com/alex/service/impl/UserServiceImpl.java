package com.alex.service.impl;

import com.alex.model.User;
import com.alex.repo.UserRepo;
import com.alex.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public User getUserByName(String username) {
        return userRepo.getUserByName(username);
    }
}
