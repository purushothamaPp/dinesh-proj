package com.creative.hfs.hfsbackend.controller;

import com.creative.hfs.hfsbackend.auth.JwtUtil;
import com.creative.hfs.hfsbackend.model.dto.*;
import com.creative.hfs.hfsbackend.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    private EmployeeService employeeService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/generatetoken")
    public ResponseEntity<?> login(@RequestBody LoginReq loginReq) {
        log.info("generatetoken(-) started");
        log.info("userId is :" + loginReq.getUserId());

        try {
            String hashedInputPassword;

            // Check if the login request is for the admin user
            if (loginReq.getUserId() == 200) {
                // Use the hardcoded password for the admin user
                hashedInputPassword = "adminPass";
            } else {
                // For non-admin users, hash the password
                hashedInputPassword = hashPassword(loginReq.getPassword());
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginReq.getUserId(), hashedInputPassword));
            Integer userId = Integer.parseInt(authentication.getName());

            User user = new User(userId, hashedInputPassword);

            // Check user active status before generating token
            boolean activeStatus = isActiveEmployee(userId);
            if (!activeStatus) {
                log.info("User is not active. Token generation skipped.");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorRes(HttpStatus.FORBIDDEN, "User not active"));
            }

            String token = jwtUtil.createToken(user);
            LoginRes loginRes = new LoginRes(userId, token);
            log.info("generate token(-) completed");
            return ResponseEntity.ok(loginRes);

        } catch (BadCredentialsException e) {
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, "Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }


    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            // Handle hashing algorithm not found exception
            e.printStackTrace();
            return null;
        }
    }


    private boolean isActiveEmployee(Integer employeeId) {
        Optional<EmployeeDTO> employeeDto = employeeService.findEmployeeDtoById(employeeId);
        return employeeDto.isPresent() && employeeDto.get().getStatus().equals("Active");
    }


}
