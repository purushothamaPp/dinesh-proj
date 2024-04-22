package com.creative.hfs.hfsbackend.model.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepartmentTest {

    @Test
    public void testDepartmentGettersAndSetters() {
        // Create an instance of Department
        Department department = new Department();

        // Set values using setters
        department.setDepartmentId(1);
        department.setDepartmentName("Engineering");

        // Verify values using getters
        assertEquals(1, department.getDepartmentId());
        assertEquals("Engineering", department.getDepartmentName());
    }

    @Test
    public void testDepartmentConstructorWithArgs() {
        // Create an instance of Department using the constructor with arguments
        Department department = new Department(1, "Engineering");

        // Verify values using getters
        assertEquals(1, department.getDepartmentId());
        assertEquals("Engineering", department.getDepartmentName());
    }

    @Test
    public void testDepartmentNoArgsConstructor() {
        // Create an instance of Department using the no-args constructor
        Department department = new Department();

        // Set values using setters
        department.setDepartmentId(1);
        department.setDepartmentName("Engineering");

        // Verify values using getters
        assertEquals(1, department.getDepartmentId());
        assertEquals("Engineering", department.getDepartmentName());
    }
}
