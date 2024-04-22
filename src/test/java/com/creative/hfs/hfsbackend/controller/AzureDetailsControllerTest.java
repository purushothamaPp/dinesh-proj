package com.creative.hfs.hfsbackend.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.creative.hfs.hfsbackend.model.entity.AzureDetails;
import com.creative.hfs.hfsbackend.repository.AzureDetailsRepository;
import com.creative.hfs.hfsbackend.service.AzureDetailsService;



//@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class AzureDetailsControllerTest {
	
	 @Autowired
	    private MockMvc mockMvc;

	    @MockBean
	    private AzureDetailsService azureDetailsService;

//	    @Autowired
//	    private AzureDetailsRepository azureDetailsRepo; // Make sure this is properly configured as a Spring bean

	    @Test
		@Disabled
	    public void testGetEmployeeDetails() throws Exception {
	        Integer employeeId = 11200;
	        AzureDetails expectedDetails = new AzureDetails(11200, "Aarav", "Sharma");
	        when(azureDetailsService.getEmployeeDetails(employeeId)).thenReturn(expectedDetails);

	        // Perform the GET request and validate the response
	        mockMvc.perform(MockMvcRequestBuilders.get("/api/azureDetails/{employeeId}", employeeId))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").value(expectedDetails.getEmployeeId()))
	                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(expectedDetails.getFirstName()))
	                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(expectedDetails.getLastName()));
	    }
}
