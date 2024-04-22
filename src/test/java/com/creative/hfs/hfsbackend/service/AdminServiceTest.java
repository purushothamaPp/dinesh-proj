package com.creative.hfs.hfsbackend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.creative.hfs.hfsbackend.model.dto.DepartmentDTO;
import com.creative.hfs.hfsbackend.model.entity.Department;
import com.creative.hfs.hfsbackend.model.entity.Employee;
import com.creative.hfs.hfsbackend.model.entity.Role;
import com.creative.hfs.hfsbackend.repository.DepartmentRepository;
import com.creative.hfs.hfsbackend.repository.EmployeeRepository;
import com.creative.hfs.hfsbackend.repository.RoleRepository;

@SpringBootTest
class AdminServiceTest {

	@Mock
	private EmployeeRepository employeeRepository;

	@Mock
	private RoleRepository roleRepository;

	@Mock
	private DepartmentRepository departmentRepository;

	@InjectMocks
	private AdminService adminService;

	@Test
	public void testGetAllRoles() {
		// Mock data

		Role role1 = new Role(100, "HRBP");
		Role role2 = new Role(200, "Manager");
		List<Role> mockRoles = Arrays.asList(role1, role2);

		// Mock repository response
		when(roleRepository.findAll()).thenReturn(mockRoles);

		// Call the service method
		List<Role> result = adminService.getAllRoles();

		// Verify the result
		assertEquals(2, result.size());
		// Add more assertions based on your specific requirements
	}

	// Write similar tests for other methods in AdminService

	@Test
	public void testGetAllDepartments() {

		// Mock data
		Department department1 = new Department(1, "Talent Acquisition");
		Department department2 = new Department(2, "Talent Requirement");
		List<Department> mockDepartments = Arrays.asList(department1, department2);

		// Mock repository response
		when(departmentRepository.findAll()).thenReturn(mockDepartments);

		// Call the service method
		List<Department> result = adminService.getAllDepartments();

		// Verify the result
		assertEquals(2, result.size());
		// Add more assertions based on your specific requirements
	}

	@Test
	public void testGetAllManagers() {
		// Mock data

		Employee manager1 = new Employee();
		manager1.setEmployeeId(11200);
		manager1.setFirstName("Aarav");
		manager1.setLastName("Sharma");
		Employee manager2 = new Employee();
		manager2.setEmployeeId(11201);
		manager2.setFirstName("Aditi");
		manager2.setLastName("Patel");

		List<Employee> mockManagers = Arrays.asList(manager1, manager2);

		// Mock repository response
		when(employeeRepository.findByRole_RoleName("Manager")).thenReturn(mockManagers);

		// Call the service method
		List<Employee> result = adminService.getAllManagers();

		// Verify the result
		assertEquals(2, result.size());
		// Add more assertions based on your specific requirements
	}

	@Test
	public void testGetAllBuHeads() {
		// Mock data
		Employee buHead1 = new Employee();
		buHead1.setEmployeeId(11200);
		buHead1.setFirstName("Aarav");
		buHead1.setLastName("Sharma");
		Employee buHead2 = new Employee();
		buHead2.setEmployeeId(11201);
		buHead2.setFirstName("Aditi");
		buHead2.setLastName("Patel");

		List<Employee> mockBuHeads = Arrays.asList(buHead1, buHead2);

		// Mock repository response
		when(employeeRepository.findByRole_RoleName("Bu_head")).thenReturn(mockBuHeads);

		// Call the service method
		List<Employee> result = adminService.getAllBuHeads();

		// Verify the result
		assertEquals(2, result.size());
		// Add more assertions based on your specific requirements
	}

	@Test
	public void testGetManagerByDepartment() {
		// Mock data
		String department = "Talent Acquisition";

		Employee manager = new Employee();
		manager.setEmployeeId(11200);
		manager.setFirstName("Aarav");
		manager.setLastName("Sharma");

		// Mock repository response
		when(employeeRepository.findTopByDepartment_DepartmentNameAndRole_RoleName("Manager", department))
				.thenReturn(manager);

		// Call the service method
		Employee result = adminService.getManagerByDepartment(department);

		// Verify the result
		assertEquals(manager, result);
		// Add more assertions based on your specific requirements
	}

	@Test
	public void testGetBuHeadByDepartment() {
		// Mock data
		String department = "Talent Acquisition";
		Employee buHead = new Employee();
		buHead.setEmployeeId(11200);
		buHead.setFirstName("Aarav");
		buHead.setLastName("Sharma");

		// Mock repository response
		when(employeeRepository.findTopByDepartment_DepartmentNameAndRole_RoleName("Bu_head", department))
				.thenReturn(buHead);

		// Call the service method
		Employee result = adminService.getBuHeadByDepartment(department);

		// Verify the result
		assertEquals(buHead, result);
		// Add more assertions based on your specific requirements
	}

	@Test
	public void testInsertEmployee() {
		// Mock data
		Employee employee1 = new Employee();
		employee1.setEmployeeId(12345);
		employee1.setFirstName("Aarav");
		employee1.setLastName("Sharma");

		// Call the service method
		adminService.insertEmployee(employee1);

		// Verify that the save method was called on the repository with the correct
		// argument
		verify(employeeRepository, times(1)).save(employee1);
	}

	@Test
	public void testGetEmployeeById() {
		// Mock data
		Integer employeeId = 11200;

		Employee employee = new Employee();
		employee.setEmployeeId(11200);
		employee.setFirstName("Aarav");
		employee.setLastName("Sharma");
		// Mock repository response
		when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

		// Call the service method
		Optional<Employee> result = adminService.getEmployeeById(employeeId);

		// Verify the result
		assertEquals(Optional.of(employee), result);
	}

	@Test
	public void testUpdateEmployee() {
		// Mock data
		Employee existingEmployee = new Employee();
		existingEmployee.setEmployeeId(11200);
		existingEmployee.setFirstName("Aarav");
		existingEmployee.setLastName("Sharma");

		// Call the service method
		adminService.updateEmployee(existingEmployee);

		// Verify that the save method was called on the repository with the correct
		// argument
		verify(employeeRepository, times(1)).save(existingEmployee);
	}

}
