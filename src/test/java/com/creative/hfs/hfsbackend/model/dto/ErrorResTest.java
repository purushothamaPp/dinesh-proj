package com.creative.hfs.hfsbackend.model.dto;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class ErrorResTest {

    @Test
    public void testErrorResGettersAndSetters() {
        // Create an instance of ErrorRes
        ErrorRes errorRes = new ErrorRes();

        // Set values using setters
        errorRes.setHttpStatus(HttpStatus.NOT_FOUND);
        errorRes.setMessage("Resource not found");

        // Verify values using getters
        assertEquals(HttpStatus.NOT_FOUND, errorRes.getHttpStatus());
        assertEquals("Resource not found", errorRes.getMessage());
    }

    @Test
    public void testErrorResConstructorWithArgs() {
        // Create an instance of ErrorRes using the constructor with arguments
        ErrorRes errorRes = new ErrorRes(HttpStatus.NOT_FOUND, "Resource not found");

        // Verify values using getters
        assertEquals(HttpStatus.NOT_FOUND, errorRes.getHttpStatus());
        assertEquals("Resource not found", errorRes.getMessage());
    }

    @Test
    public void testErrorResNoArgsConstructor() {
        // Create an instance of ErrorRes using the no-args constructor
        ErrorRes errorRes = new ErrorRes();

        // Set values using setters
        errorRes.setHttpStatus(HttpStatus.NOT_FOUND);
        errorRes.setMessage("Resource not found");

        // Verify values using getters
        assertEquals(HttpStatus.NOT_FOUND, errorRes.getHttpStatus());
        assertEquals("Resource not found", errorRes.getMessage());
    }
}
