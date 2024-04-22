package com.creative.hfs.hfsbackend.model.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AzureDetailsTest {

    @Test
    public void testAzureDetailsGettersAndSetters() {
        // Create an instance of AzureDetails
        AzureDetails azureDetails = new AzureDetails();

        // Set values using setters
        azureDetails.setEmployeeId(1);
        azureDetails.setFirstName("John");
        azureDetails.setLastName("Doe");

        // Verify values using getters
        assertEquals(1, azureDetails.getEmployeeId());
        assertEquals("John", azureDetails.getFirstName());
        assertEquals("Doe", azureDetails.getLastName());
    }

    @Test
    public void testAzureDetailsConstructor() {
        // Create an instance of AzureDetails using the constructor
        AzureDetails azureDetails = new AzureDetails(1, "John", "Doe");

        // Verify values using getters
        assertEquals(1, azureDetails.getEmployeeId());
        assertEquals("John", azureDetails.getFirstName());
        assertEquals("Doe", azureDetails.getLastName());
    }
}

