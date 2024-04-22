package com.creative.hfs.hfsbackend.model.dto;

import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class EmployeeDTOTest {

    @Test
    public void testEmployeeDTOGettersAndSetters() {
        // Create instances of related DTOs and entities
        RoleDTO roleDTO = new RoleDTO(1, "Developer");
        DepartmentDTO departmentDTO = new DepartmentDTO(1, "Engineering");

        // Create an instance of EmployeeDTO
        EmployeeDTO employeeDTO = new EmployeeDTO();

        // Set values using setters
        employeeDTO.setEmployeeId(1);
        employeeDTO.setFirstName("John");
        employeeDTO.setLastName("Doe");
        employeeDTO.setRole(roleDTO);
        employeeDTO.setDepartment(departmentDTO);
        employeeDTO.setManager(null); // Set to null for simplicity
        employeeDTO.setBuHead(null); // Set to null for simplicity
        employeeDTO.setHireDate(new Date());
        employeeDTO.setStatus("Active");

        // Verify values using getters
        assertEquals(1, employeeDTO.getEmployeeId());
        assertEquals("John", employeeDTO.getFirstName());
        assertEquals("Doe", employeeDTO.getLastName());
        assertEquals(roleDTO, employeeDTO.getRole());
        assertEquals(departmentDTO, employeeDTO.getDepartment());
        assertEquals(null, employeeDTO.getManager()); // Verify with actual manager instance if needed
        assertEquals(null, employeeDTO.getBuHead()); // Verify with actual buHead instance if needed
        // Verify other fields as needed
    }

//    @Test
//    public void testEmployeeDTOConstructorWithArgs() {
//        // Create instances of related DTOs and entities
//        RoleDTO roleDTO = new RoleDTO(1, "Developer");
//        DepartmentDTO departmentDTO = new DepartmentDTO(1, "Engineering");
//
//        // Create an instance of EmployeeDTO using the constructor with arguments
//        EmployeeDTO employeeDTO = new EmployeeDTO(1, "John", "Doe", roleDTO, departmentDTO, null, null, new Date(), "Active");
//
//        // Verify values using getters
//        assertEquals(1, employeeDTO.getEmployeeId());
//        assertEquals("John", employeeDTO.getFirstName());
//        assertEquals("Doe", employeeDTO.getLastName());
//        assertEquals(roleDTO, employeeDTO.getRole());
//        assertEquals(departmentDTO, employeeDTO.getDepartment());
//        assertEquals(null, employeeDTO.getManager()); // Verify with actual manager instance if needed
//        assertEquals(null, employeeDTO.getBuHead()); // Verify with actual buHead instance if needed
//        // Verify other fields as needed
//    }

    @Test
    public void testEmployeeDTONoArgsConstructor() {
        // Create an instance of EmployeeDTO using the no-args constructor
        EmployeeDTO employeeDTO = new EmployeeDTO();

        // Set values using setters
        employeeDTO.setEmployeeId(1);
        employeeDTO.setFirstName("John");
        employeeDTO.setLastName("Doe");

        // Verify values using getters
        assertEquals(1, employeeDTO.getEmployeeId());
        assertEquals("John", employeeDTO.getFirstName());
        assertEquals("Doe", employeeDTO.getLastName());
    }
}
