package com.creative.hfs.hfsbackend.model.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginResTest {

    @Test
    public void testLoginResGettersAndSetters() {
        // Create an instance of LoginRes
        LoginRes loginRes = new LoginRes();

        // Set values using setters
        loginRes.setUserId(1);
        loginRes.setToken("token123");

        // Verify values using getters
        assertEquals(1, loginRes.getUserId());
        assertEquals("token123", loginRes.getToken());
    }

    @Test
    public void testLoginResConstructorWithArgs() {
        // Create an instance of LoginRes using the constructor with arguments
        LoginRes loginRes = new LoginRes(1, "token123");

        // Verify values using getters
        assertEquals(1, loginRes.getUserId());
        assertEquals("token123", loginRes.getToken());
    }

    @Test
    public void testLoginResNoArgsConstructor() {
        // Create an instance of LoginRes using the no-args constructor
        LoginRes loginRes = new LoginRes();

        // Set values using setters
        loginRes.setUserId(1);
        loginRes.setToken("token123");

        // Verify values using getters
        assertEquals(1, loginRes.getUserId());
        assertEquals("token123", loginRes.getToken());
    }
}
