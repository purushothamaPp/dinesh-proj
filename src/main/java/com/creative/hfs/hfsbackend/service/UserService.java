package com.creative.hfs.hfsbackend.service;

import com.creative.hfs.hfsbackend.model.entity.User;
import com.creative.hfs.hfsbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public User getUser(Integer userId){
        User user=userRepository.findByUserId(userId);
        return user;

    }


    public User updateUser(User user){
        userRepository.save(user);
        return user;
    }
}
