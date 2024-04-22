package com.creative.hfs.hfsbackend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.creative.hfs.hfsbackend.model.entity.AzureDetails;
import com.creative.hfs.hfsbackend.repository.AzureDetailsRepository;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
class AzureDetailsServiceTest {
	@Mock
	private AzureDetailsRepository azureDetailsRepo;

	@InjectMocks
	private AzureDetailsService azureDetailsService;

	@Test
	public void testGetEmployeeDetails() {
		Integer employeeId = 11200;

		AzureDetails expectedDetails = new AzureDetails(11200, "Aarav", "Sharma");
		when(azureDetailsRepo.findByEmployeeId(employeeId)).thenReturn(expectedDetails);
		AzureDetails actualDetails = azureDetailsService.getEmployeeDetails(employeeId);
		assertEquals(expectedDetails.getFirstName(), actualDetails.getFirstName());
		assertEquals(expectedDetails.getLastName(), actualDetails.getLastName());
		// argument
//		verify(azureDetailsRepo).findByEmployeeId(employeeId);
	}

}
