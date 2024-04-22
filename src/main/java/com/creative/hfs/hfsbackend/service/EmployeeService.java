package com.creative.hfs.hfsbackend.service;

import com.creative.hfs.hfsbackend.model.dto.DepartmentDTO;
import com.creative.hfs.hfsbackend.model.dto.EmployeeDTO;
import com.creative.hfs.hfsbackend.model.dto.RoleDTO;
import com.creative.hfs.hfsbackend.model.entity.Employee;
import com.creative.hfs.hfsbackend.model.entity.HrbpDepartmentMapping;
import com.creative.hfs.hfsbackend.model.mapper.DepartmentMapper;
import com.creative.hfs.hfsbackend.model.mapper.EmployeeMapper;
import com.creative.hfs.hfsbackend.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private DepartmentMapper departmentMapper;



    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(employee -> {
                    EmployeeDTO employeeDTO = employeeMapper.toDto(employee);
                    List<HrbpDepartmentMapping> mappings = employee.getDepartmentMappings(); // Assuming you have this method in your Employee entity
                    List<DepartmentDTO> departmentDTOs = mappings.stream()
                            .map(mapping -> departmentMapper.toDto(mapping.getDepartment())) // Assuming you have a DepartmentMapper
                            .collect(Collectors.toList());
                    employeeDTO.setDepartments(departmentDTOs);
                    return employeeDTO;
                })
                .collect(Collectors.toList());

    }


    public Optional<EmployeeDTO> findEmployeeDtoById(Integer employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        return employee.map(employeeMapper::toDto);
    }

    public List<RoleDTO> getAllRoles() {
        return employeeRepository.findAllRoles();
    }

    public List<DepartmentDTO> getAllDepartments() {
        return employeeRepository.findAllDepartments();
    }

    public List<EmployeeDTO> findEmployeesBy_MANAGER_ROLE() {
        return employeeRepository.findEmployeesBy_MANAGER_ROLE();
    }

    public List<EmployeeDTO> findEmployeesBy_BU_HEAD_ROLE() {
        return employeeRepository.findEmployeesBy_BU_HEAD_ROLE();
    }
}
