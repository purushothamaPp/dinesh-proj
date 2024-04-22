package com.creative.hfs.hfsbackend.model.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginReqTest {

    @Test
    public void testLoginReqGettersAndSetters() {
        // Create an instance of LoginReq
        LoginReq loginReq = new LoginReq();

        // Set values using setters
        loginReq.setUserId(1);
        loginReq.setPassword("password123");

        // Verify values using getters
        assertEquals(1, loginReq.getUserId());
        assertEquals("password123", loginReq.getPassword());
    }

    @Test
    public void testLoginReqConstructorWithArgs() {
        // Create an instance of LoginReq using the constructor with arguments
        LoginReq loginReq = new LoginReq(1, "password123");

        // Verify values using getters
        assertEquals(1, loginReq.getUserId());
        assertEquals("password123", loginReq.getPassword());
    }

    @Test
    public void testLoginReqNoArgsConstructor() {
        // Create an instance of LoginReq using the no-args constructor
        LoginReq loginReq = new LoginReq();

        // Set values using setters
        loginReq.setUserId(1);
        loginReq.setPassword("password123");

        // Verify values using getters
        assertEquals(1, loginReq.getUserId());
        assertEquals("password123", loginReq.getPassword());
    }
}
