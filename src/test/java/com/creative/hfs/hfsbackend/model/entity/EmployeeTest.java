package com.creative.hfs.hfsbackend.model.entity;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeTest {

    @Test
    public void testEmployeeGettersAndSetters() {
        // Create instances of related entities
        Role role = new Role(1, "Developer");
        Department department = new Department(1, "Engineering");

        // Create an instance of Employee
        Employee employee = new Employee();

        // Set values using setters
        employee.setEmployeeId(1);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setRole(role);
        employee.setDepartment(department);
        employee.setManager(null); // Set to null for simplicity
        employee.setBuHead(null); // Set to null for simplicity
        employee.setHireDate(new Date());
        employee.setStatus("Active");

        // Verify values using getters
        assertEquals(1, employee.getEmployeeId());
        assertEquals("John", employee.getFirstName());
        assertEquals("Doe", employee.getLastName());
        assertEquals(role, employee.getRole());
        assertEquals(department, employee.getDepartment());
        assertEquals(null, employee.getManager()); // Verify with actual manager instance if needed
        assertEquals(null, employee.getBuHead()); // Verify with actual buHead instance if needed
        // Verify other fields as needed
    }

    @Test
    public void testEmployeeConstructorWithArgs() {
        // Create instances of related entities
        Role role = new Role(1, "Developer");
        Department department = new Department(1, "Engineering");

        // Create an instance of Employee using the constructor with arguments
        Employee employee = new Employee(1, "John", "Doe", role, department, null, null, new Date(), "Active");

        // Verify values using getters
        assertEquals(1, employee.getEmployeeId());
        assertEquals("John", employee.getFirstName());
        assertEquals("Doe", employee.getLastName());
        assertEquals(role, employee.getRole());
        assertEquals(department, employee.getDepartment());
        assertEquals(null, employee.getManager()); // Verify with actual manager instance if needed
        assertEquals(null, employee.getBuHead()); // Verify with actual buHead instance if needed
        // Verify other fields as needed
    }
}
