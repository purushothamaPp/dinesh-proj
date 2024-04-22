package com.creative.hfs.hfsbackend.model.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    @Test
    public void testUserGettersAndSetters() {
        // Create an instance of User
        User user = new User();

        // Set values using setters
        user.setUserId(1);
        user.setPassword("password123");

        // Verify values using getters
        assertEquals(1, user.getUserId());
        assertEquals("password123", user.getPassword());
    }

    @Test
    public void testUserConstructorWithArgs() {
        // Create an instance of User using the constructor with arguments
        User user = new User(1, "password123");

        // Verify values using getters
        assertEquals(1, user.getUserId());
        assertEquals("password123", user.getPassword());
    }

    @Test
    public void testUserNoArgsConstructor() {
        // Create an instance of User using the no-args constructor
        User user = new User();

        // Set values using setters
        user.setUserId(1);
        user.setPassword("password123");

        // Verify values using getters
        assertEquals(1, user.getUserId());
        assertEquals("password123", user.getPassword());
    }
}
