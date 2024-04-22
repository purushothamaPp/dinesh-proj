package com.creative.hfs.hfsbackend.model.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class DepartmentDTOTest {

    @Test
    public void testDepartmentDTOGettersAndSetters() {
        // Create an instance of DepartmentDTO
        DepartmentDTO departmentDTO = new DepartmentDTO();

        // Set values using setters
        departmentDTO.setDepartmentId(1);
        departmentDTO.setDepartmentName("Engineering");

        // Verify values using getters
        assertEquals(1, departmentDTO.getDepartmentId());
        assertEquals("Engineering", departmentDTO.getDepartmentName());
    }

    @Test
    public void testDepartmentDTOConstructorWithArgs() {
        // Create an instance of DepartmentDTO using the constructor with arguments
        DepartmentDTO departmentDTO = new DepartmentDTO(1, "Engineering");

        // Verify values using getters
        assertEquals(1, departmentDTO.getDepartmentId());
        assertEquals("Engineering", departmentDTO.getDepartmentName());
    }

    @Test
    public void testDepartmentDTONoArgsConstructor() {
        // Create an instance of DepartmentDTO using the no-args constructor
        DepartmentDTO departmentDTO = new DepartmentDTO();

        // Set values using setters
        departmentDTO.setDepartmentId(1);
        departmentDTO.setDepartmentName("Engineering");

        // Verify values using getters
        assertEquals(1, departmentDTO.getDepartmentId());
        assertEquals("Engineering", departmentDTO.getDepartmentName());
    }
}
