package com.creative.hfs.hfsbackend.service;

import com.creative.hfs.hfsbackend.model.entity.Department;
import com.creative.hfs.hfsbackend.model.entity.Employee;
import com.creative.hfs.hfsbackend.model.entity.Role;
import com.creative.hfs.hfsbackend.repository.DepartmentRepository;
import com.creative.hfs.hfsbackend.repository.EmployeeRepository;
import com.creative.hfs.hfsbackend.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AdminService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public List<Employee> getAllManagers() {
        return employeeRepository.findByRole_RoleName("Manager");
    }

    public List<Employee> getAllBuHeads() {
        return employeeRepository.findByRole_RoleName("Bu_head");
    }

    public Employee getManagerByDepartment(String department) {
        return employeeRepository.findTopByDepartment_DepartmentNameAndRole_RoleName("Manager", department);
    }

    public Employee getBuHeadByDepartment(String department) {
        return employeeRepository.findTopByDepartment_DepartmentNameAndRole_RoleName("Bu_head", department);
    }

    public Employee insertEmployee(Employee employee) {
       return employeeRepository.save(employee);

    }

    public Optional<Employee> getEmployeeById(Integer employeeId) {
        return employeeRepository.findById(employeeId);
    }

    public void updateEmployee(Employee existingEmployee) {
        employeeRepository.save(existingEmployee);
    }

    public boolean isEmployeeIdExists(Integer employeeId) {
        // Use the repository or DAO method to check if the employee ID exists
        return employeeRepository.existsByEmployeeId(employeeId); // Change this according to your repository or DAO method
    }
}
