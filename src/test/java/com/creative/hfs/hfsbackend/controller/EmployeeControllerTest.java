package com.creative.hfs.hfsbackend.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.creative.hfs.hfsbackend.model.dto.EmployeeDTO;
import com.creative.hfs.hfsbackend.service.EmployeeService;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;
    
    @InjectMocks
    private EmployeeController employeeController;

    @Test
    @Disabled
    public void testFindEmployeeDetails() throws Exception {
        // Mock the behavior of the EmployeeService
        EmployeeDTO sampleEmployee = new EmployeeDTO(11200, "Aarav", "Sharma");
        when(employeeService.findEmployeeDtoById(11200)).thenReturn(Optional.of(sampleEmployee));

        // Perform the test
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employeeDetails/11200")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"employeeId\":11200,\"firstName\":\"Aarav\",\"lastName\":\"Sharma\"}"));
    }

    @Test
    @Disabled
    public void testFindEmployeeDetailsNotFound() throws Exception {
        // Mock the behavior of the EmployeeService
        when(employeeService.findEmployeeDtoById(2)).thenReturn(Optional.empty());

        // Perform the test for a not found case
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employeeDetails/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("No Employee exists with ID: 2, try with an existing employee!"));
    }
    


}
