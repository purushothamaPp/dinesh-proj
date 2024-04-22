package com.creative.hfs.hfsbackend.controller;

import com.creative.hfs.hfsbackend.exceptions.ResourceNotFoundException;
import com.creative.hfs.hfsbackend.model.dto.DepartmentDTO;
import com.creative.hfs.hfsbackend.model.dto.EmployeeDTO;
import com.creative.hfs.hfsbackend.service.EmployeeService;
import com.creative.hfs.hfsbackend.service.HrbpDepartmentMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Slf4j
@CrossOrigin("*")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private HrbpDepartmentMappingService hrbpDepartmentMappingService;

    @GetMapping("/employeeList")
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/employeeDetails/{employeeId}")
    public ResponseEntity<EmployeeDTO> findEmployeeDetails(@PathVariable Integer employeeId) {
        log.info("findEmployeeDetails started");
        Optional<EmployeeDTO> employeeDtoOptional = employeeService.findEmployeeDtoById(employeeId);

        if (employeeDtoOptional.isPresent()) {
            EmployeeDTO employeeDto = employeeDtoOptional.get();
            log.info("Employee Data & Dashboard data successfully fetched");
            log.info("findEmployeeDetails completed");
            return ResponseEntity.ok(employeeDto);
        } else {
            throw new ResourceNotFoundException("No Employee exists with ID: " + employeeId + ", try with an existing employee!");
        }
    }

    @GetMapping("/rolesAndDepartments")
    public Map<String, List<?>> getRolesAndDepartments() {
        Map<String, List<?>> result = new HashMap<>();
        result.put("roles", employeeService.getAllRoles());
        result.put("departments", employeeService.getAllDepartments());
        result.put("listOfManagers", employeeService.findEmployeesBy_MANAGER_ROLE());
        result.put("listOfBuHeads", employeeService.findEmployeesBy_BU_HEAD_ROLE());
        return result;
    }

    @GetMapping("/departments/{employeeId}")
    public List<DepartmentDTO> getDepartmentsByEmployeeId(@PathVariable Integer employeeId) {
        return hrbpDepartmentMappingService.getDepartmentsByEmployeeId(employeeId);
    }
}
