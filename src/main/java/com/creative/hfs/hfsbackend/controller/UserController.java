package com.creative.hfs.hfsbackend.controller;

import com.creative.hfs.hfsbackend.model.entity.User;
import com.creative.hfs.hfsbackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@RestController
@RequestMapping("/api")
@Slf4j
@CrossOrigin("*")
public class UserController {
    private static final String DEFAULT_PASSWORD = "Creative@123";
    @Autowired
    UserService userService;
    


    @PutMapping("resetUserPwd/{userId}/{newPassword}")
    public User resetPassword(@PathVariable Integer userId, @PathVariable String newPassword){
        log.info("resetPassword entered");
        User user= userService.getUser(userId);
        user.setIsThisDefaultPWD("NO");
        user.setPassword(hashPassword(newPassword));
        userService.updateUser(user);
        return user;
    }

    @PutMapping("resetUserDefaultPwd/{userId}")
    public User resetToDefaultPassword(@PathVariable Integer userId){
        log.info("resetPassword entered");
        User user= userService.getUser(userId);
        user.setIsThisDefaultPWD("YES");
        user.setPassword(hashPassword(DEFAULT_PASSWORD));
        userService.updateUser(user);
        return user;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/defaultPwdStatus/{userId}")
    public String getDefaultPasswordStatus(@PathVariable Integer userId) {
        log.info("Fetching default password status for user with ID: {}", userId);
        User user = userService.getUser(userId);
        return user.getIsThisDefaultPWD();
    }


}
