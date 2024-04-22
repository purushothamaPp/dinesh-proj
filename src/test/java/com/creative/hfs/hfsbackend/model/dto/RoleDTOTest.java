package com.creative.hfs.hfsbackend.model.dto;

import com.creative.hfs.hfsbackend.model.dto.RoleDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoleDTOTest {

    @Test
    public void testRoleDTOGettersAndSetters() {
        // Create an instance of RoleDTO
        RoleDTO roleDTO = new RoleDTO();

        // Set values using setters
        roleDTO.setRoleId(1);
        roleDTO.setRoleName("Developer");

        // Verify values using getters
        assertEquals(1, roleDTO.getRoleId());
        assertEquals("Developer", roleDTO.getRoleName());
    }

    @Test
    public void testRoleDTOConstructorWithArgs() {
        // Create an instance of RoleDTO using the constructor with arguments
        RoleDTO roleDTO = new RoleDTO(1, "Developer");

        // Verify values using getters
        assertEquals(1, roleDTO.getRoleId());
        assertEquals("Developer", roleDTO.getRoleName());
    }

    @Test
    public void testRoleDTONoArgsConstructor() {
        // Create an instance of RoleDTO using the no-args constructor
        RoleDTO roleDTO = new RoleDTO();

        // Set values using setters
        roleDTO.setRoleId(1);
        roleDTO.setRoleName("Developer");

        // Verify values using getters
        assertEquals(1, roleDTO.getRoleId());
        assertEquals("Developer", roleDTO.getRoleName());
    }
}
