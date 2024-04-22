package com.creative.hfs.hfsbackend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.creative.hfs.hfsbackend.model.dto.DepartmentDTO;
import com.creative.hfs.hfsbackend.model.dto.EmployeeDTO;
import com.creative.hfs.hfsbackend.model.dto.RoleDTO;
import com.creative.hfs.hfsbackend.model.entity.Department;
import com.creative.hfs.hfsbackend.model.entity.Employee;
import com.creative.hfs.hfsbackend.model.entity.Role;
import com.creative.hfs.hfsbackend.model.mapper.EmployeeMapper;
import com.creative.hfs.hfsbackend.repository.EmployeeRepository;

@SpringBootTest
class EmployeeServiceTest {

	@Mock
	private EmployeeRepository employeeRepository;

	@Mock
	private EmployeeMapper employeeMapper;

	@InjectMocks
	private EmployeeService employeeService;

	@Test
	public void testGetAllEmployees() {
		// Mock data
		Employee employee1 = new Employee();
		employee1.setEmployeeId(11200);
		employee1.setFirstName("Aarav");
		employee1.setLastName("Sharma");
		Employee employee2 = new Employee();
		employee2.setEmployeeId(11201);
		employee2.setFirstName("Aditi");
		employee2.setLastName("Patel");

		List<Employee> mockEmployees = Arrays.asList(employee1, employee2);

		when(employeeRepository.findAll()).thenReturn(mockEmployees);

		EmployeeDTO employeeDTO1 = new EmployeeDTO();
		EmployeeDTO employeeDTO2 = new EmployeeDTO();

		employeeDTO1.setEmployeeId(11200);
		employeeDTO1.setFirstName("Aarav");
		employeeDTO1.setLastName("Sharma");

		employeeDTO2.setEmployeeId(11201);
		employeeDTO2.setFirstName("Aditi");
		employeeDTO2.setLastName("Patel");

		when(employeeMapper.toDto(employee1)).thenReturn(employeeDTO1);
		when(employeeMapper.toDto(employee2)).thenReturn(employeeDTO2);

		List<EmployeeDTO> result = employeeService.getAllEmployees();

		assertEquals(2, result.size());
	}

	@Test
	public void testFindEmployeeDtoById_WhenFound() {
		// Mock data
		Integer employeeId = 11200;

		Employee employee = new Employee();
		employee.setEmployeeId(11201);
		employee.setFirstName("Aditi");
		employee.setLastName("Patel");

		// Mock repository response
		when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

		// Mock mapper response
		EmployeeDTO employeeDTO = new EmployeeDTO(/* set DTO properties */);
		when(employeeMapper.toDto(employee)).thenReturn(employeeDTO);

		// Call the service method
		Optional<EmployeeDTO> result = employeeService.findEmployeeDtoById(employeeId);

		// Verify the result
		assertEquals(Optional.of(employeeDTO), result);
		// Add more assertions based on your specific requirements
	}

	@Test
	public void testFindEmployeeDtoById_WhenNotFound() {
		// Mock data
		Integer employeeId = 1;

		// Mock repository response
		when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

		// Call the service method
		Optional<EmployeeDTO> result = employeeService.findEmployeeDtoById(employeeId);

		// Verify the result
		assertEquals(Optional.empty(), result);
		// Add more assertions based on your specific requirements
	}

	@Test
	public void testGetAllRoles() {
		// Mock data
		RoleDTO role1 = new RoleDTO(100, "HRBP");
		RoleDTO role2 = new RoleDTO(200, "Manager");
		List<RoleDTO> mockRoles = Arrays.asList(role1, role2);

		// Mock repository response
		when(employeeRepository.findAllRoles()).thenReturn(mockRoles);

		// Call the service method
		List<RoleDTO> result = employeeService.getAllRoles();

		// Verify the result
		assertEquals(2, result.size());
		// Add more assertions based on your specific requirements
	}

	@Test
	public void testGetAllDepartments() {
		// Mock data
		DepartmentDTO department1 = new DepartmentDTO(1, "Talent Acquisition");
		DepartmentDTO department2 = new DepartmentDTO(2, "Talent Requirement");
		List<DepartmentDTO> mockDepartments = Arrays.asList(department1, department2);

		// Mock repository response
		when(employeeRepository.findAllDepartments()).thenReturn(mockDepartments);

		// Call the service method
		List<DepartmentDTO> result = employeeService.getAllDepartments();

		// Verify the result
		assertEquals(2, result.size());
		// Add more assertions based on your specific requirements
	}

	@Test
	public void testFindEmployeesBy_MANAGER_ROLE() {

		EmployeeDTO employeeDTO1 = new EmployeeDTO();
		EmployeeDTO employeeDTO2 = new EmployeeDTO();

		employeeDTO1.setEmployeeId(11200);
		employeeDTO1.setFirstName("Aarav");
		employeeDTO1.setLastName("Sharma");

		employeeDTO2.setEmployeeId(11201);
		employeeDTO2.setFirstName("Aditi");
		employeeDTO2.setLastName("Patel");

		List<EmployeeDTO> mockEmployees = Arrays.asList(employeeDTO1, employeeDTO2);

		// Mock repository response
		when(employeeRepository.findEmployeesBy_MANAGER_ROLE()).thenReturn(mockEmployees);

		// Call the service method
		List<EmployeeDTO> result = employeeService.findEmployeesBy_MANAGER_ROLE();

		// Verify the result
		assertEquals(2, result.size());
		// Add more assertions based on your specific requirements
	}

	@Test
	public void testFindEmployeesBy_BU_HEAD_ROLE() {
		// Mock data
		EmployeeDTO employeeDTO1 = new EmployeeDTO();
		EmployeeDTO employeeDTO2 = new EmployeeDTO();

		employeeDTO1.setEmployeeId(11200);
		employeeDTO1.setFirstName("Aarav");
		employeeDTO1.setLastName("Sharma");

		employeeDTO2.setEmployeeId(11201);
		employeeDTO2.setFirstName("Aditi");
		employeeDTO2.setLastName("Patel");

		List<EmployeeDTO> mockEmployees = Arrays.asList(employeeDTO1, employeeDTO2);

		// Mock repository response
		when(employeeRepository.findEmployeesBy_BU_HEAD_ROLE()).thenReturn(mockEmployees);

		// Call the service method
		List<EmployeeDTO> result = employeeService.findEmployeesBy_BU_HEAD_ROLE();

		// Verify the result
		assertEquals(2, result.size());
		// Add more assertions based on your specific requirements
	}

}
