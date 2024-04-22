package com.creative.hfs.hfsbackend.controller;

import com.creative.hfs.hfsbackend.model.dto.EmployeeRequest;
import com.creative.hfs.hfsbackend.model.entity.Department;
import com.creative.hfs.hfsbackend.model.entity.Employee;
import com.creative.hfs.hfsbackend.model.entity.EmployeeRecord;
import com.creative.hfs.hfsbackend.model.entity.HrbpDepartmentMapping;
import com.creative.hfs.hfsbackend.repository.DepartmentRepository;
import com.creative.hfs.hfsbackend.repository.EmployeeRecordRepository;
import com.creative.hfs.hfsbackend.repository.HrbpDepartmentMappingRepository;
import com.creative.hfs.hfsbackend.repository.EmployeeRepository;
import com.creative.hfs.hfsbackend.service.AdminService;
import com.creative.hfs.hfsbackend.service.EmployeeRecordService;
import com.creative.hfs.hfsbackend.service.EmployeeService;
import com.creative.hfs.hfsbackend.util.SqlQueries;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@RestController
@RequestMapping("/api")
@Slf4j
@CrossOrigin("*")
public class AdminController {



    public static final String DEFAULT_STATUS_ACTIVE = "Active";

    public static final Integer DEFAULT_HRBP_DEPARTMENT = 6;

    @Value("${admin.default.password}")
    private String defaultPassword;

    @Value("${admin.default.password.flag}")
    private String defaultPasswordFlag;


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AdminService adminService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private HrbpDepartmentMappingRepository employeeDepartmentMappingRepository;


    @Autowired
    private EmployeeRecordService employeeRecordService;

    @Autowired
    private EmployeeService employeeService;


    @PostMapping("/admin-emp")
    public ResponseEntity<EmployeeRecord> saveEmployeeRecord(@RequestBody EmployeeRecord employeeRecord) {
        if (employeeService.findEmployeeDtoById(employeeRecord.getEmployeeId()).isPresent()) {
            // If ID exists, return conflict response
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        // Set department ID based on business unit
        setDepartmentIdBasedOnBusinessUnit(employeeRecord);

        EmployeeRecord savedEmployeeRecord = employeeRecordService.saveEmployeeRecord(employeeRecord);
        return new ResponseEntity<>(savedEmployeeRecord, HttpStatus.CREATED);
    }




    @PostMapping("/admin-hr")
    public ResponseEntity<Employee> insertEmployeeHR(@RequestBody EmployeeRequest employeeRequest) {
        try {
            // Check if the employee ID already exists in the database
            if (adminService.isEmployeeIdExists(employeeRequest.getEmployeeId())) {
                // If the employee ID already exists, send a conflict response
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }

            // Extract employee data from the request
            Employee employee = new Employee();
            employee.setEmployeeId(employeeRequest.getEmployeeId());
            employee.setFirstName(employeeRequest.getFirstName());
            employee.setLastName(employeeRequest.getLastName());
            employee.setRole(employeeRequest.getRole());
            employee.setStatus(DEFAULT_STATUS_ACTIVE);

            // Set the department ID to 6
            Department department = new Department();
            department.setDepartmentId(DEFAULT_HRBP_DEPARTMENT);
            employee.setDepartment(department);


            if (employee.getRole().getRoleId() == 100) {
                employee.setManager(employeeRequest.getManager());
                employee.setBuHead(employeeRequest.getBuHead());
            } else if (employee.getRole().getRoleId() == 200) {
                employee.setManager(employeeRequest.getBuHead());
                employee.setBuHead(employeeRequest.getBuHead());
            } else {
                employee.setManager(null);
                employee.setBuHead(null);
            }

            // Save the employee entity
            Employee savedEmployee = adminService.insertEmployee(employee);

            // Check if departments are provided
            if (employeeRequest.getDepartments() != null && !employeeRequest.getDepartments().isEmpty()) {
                // Iterate over departments and save mappings
                List<Department> departments = employeeRequest.getDepartments();
                for (Department departmentMulti : departments) {
                    // Create a new mapping entity
                    HrbpDepartmentMapping mapping = new HrbpDepartmentMapping();
                    mapping.setEmployee(savedEmployee); // Use the saved employee
                    mapping.setDepartment(departmentMulti);

                    // Save the mapping entity
                    employeeDepartmentMappingRepository.save(mapping);
                }
            }

            // Set default status
            savedEmployee.setStatus(DEFAULT_STATUS_ACTIVE);




            // Insert user with default status and generated password
            jdbcTemplate.update(SqlQueries.INSERT_USER, savedEmployee.getEmployeeId(), DEFAULT_STATUS_ACTIVE, hashPassword(defaultPassword), defaultPasswordFlag);

            return ResponseEntity.ok(savedEmployee);
        } catch (Exception e) {
            log.error("Error inserting employee: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }







    @Transactional
    @PutMapping("/admin/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Integer employeeId, @RequestBody EmployeeRequest employeeRequest) {
        try {
            Optional<Employee> existingEmployeeOptional = adminService.getEmployeeById(employeeId);

            if (existingEmployeeOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            Employee existingEmployee = existingEmployeeOptional.get();

            // Update employee details
            existingEmployee.setFirstName(employeeRequest.getFirstName());
            existingEmployee.setLastName(employeeRequest.getLastName());
            existingEmployee.setManager(employeeRequest.getManager());
            existingEmployee.setBuHead(employeeRequest.getBuHead());
            existingEmployee.setStatus(employeeRequest.getStatus());

            // Update departments
            if (employeeRequest.getDepartments() != null && !employeeRequest.getDepartments().isEmpty()) {
                // Clear existing mappings
                employeeDepartmentMappingRepository.deleteByEmployee(existingEmployee);

                // Iterate over departments and save mappings
                List<Department> departments = employeeRequest.getDepartments();
                for (Department departmentMulti : departments) {
                    // Create a new mapping entity
                    HrbpDepartmentMapping mapping = new HrbpDepartmentMapping();
                    mapping.setEmployee(existingEmployee); // Use the existing employee
                    mapping.setDepartment(departmentMulti);

                    // Save the mapping entity
                    employeeDepartmentMappingRepository.save(mapping);
                }
            }

            // Update the employee
            adminService.updateEmployee(existingEmployee);
            jdbcTemplate.update(SqlQueries.UPDATE_USER, employeeRequest.getStatus(), employeeId);

            return ResponseEntity.ok(existingEmployee); // Return the updated employee
        } catch (Exception e) {
            log.error("Error updating employee: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            // Handle hashing algorithm not found exception
            e.printStackTrace();
            return null;
        }
    }

    private void setDepartmentIdBasedOnBusinessUnit(EmployeeRecord employeeRecord) {
        String businessUnitName = employeeRecord.getBusinessUnitName();

        if (businessUnitName != null) {
            // Mapping department names to enum constants
            Map<String, com.creative.hfs.hfsbackend.model.enums.Department> departmentMap = new HashMap<>();
            departmentMap.put("Digital Product Engineering (CAE)", com.creative.hfs.hfsbackend.model.enums.Department.DIGITAL_PRODUCT_ENGINEERING_CAE);
            departmentMap.put("Intelligent Connected Products", com.creative.hfs.hfsbackend.model.enums.Department.INTELLIGENT_CONNECTED_PRODUCTS);
            departmentMap.put("Digital Plant And Process Engineering", com.creative.hfs.hfsbackend.model.enums.Department.DIGITAL_PLANT_AND_PROCESS_ENGINEERING);
            departmentMap.put("Digital Product Engineering (H&I)", com.creative.hfs.hfsbackend.model.enums.Department.DIGITAL_PRODUCT_ENGINEERING_HI);
            departmentMap.put("Digital Product Engineering (ACE)", com.creative.hfs.hfsbackend.model.enums.Department.DIGITAL_PRODUCT_ENGINEERING_ACE);
            departmentMap.put("Digital Discrete Manufacturing Engineering", com.creative.hfs.hfsbackend.model.enums.Department.DIGITAL_DISCRETE_MANUFACTURING_ENGINEERING);
            departmentMap.put("In-Client Services", com.creative.hfs.hfsbackend.model.enums.Department.IN_CLIENT_SERVICES);
            departmentMap.put("Corporate", com.creative.hfs.hfsbackend.model.enums.Department.CORPORATE);
            departmentMap.put("Quality Assurance and Business Excellence", com.creative.hfs.hfsbackend.model.enums.Department.QUALITY_ASSURANCE_AND_BUSINESS_EXCELLENCE);
            departmentMap.put("Talent Acquisition", com.creative.hfs.hfsbackend.model.enums.Department.TALENT_ACQUISITION);
            departmentMap.put("Finance & Accounts", com.creative.hfs.hfsbackend.model.enums.Department.FINANCE_AND_ACCOUNTS);
            departmentMap.put("Infrastructure Management Group-Admin", com.creative.hfs.hfsbackend.model.enums.Department.INFRASTRUCTURE_MANAGEMENT_GROUP_ADMIN);
            departmentMap.put("Digital Technologies", com.creative.hfs.hfsbackend.model.enums.Department.DIGITAL_TECHNOLOGIES);
            departmentMap.put("Work Force Enablement", com.creative.hfs.hfsbackend.model.enums.Department.WORK_FORCE_ENABLEMENT);
            departmentMap.put("Human Resources", com.creative.hfs.hfsbackend.model.enums.Department.HUMAN_RESOURCES);
            departmentMap.put("Market Research and Sales Enablement", com.creative.hfs.hfsbackend.model.enums.Department.MARKET_RESEARCH_AND_SALES_ENABLEMENT);
            departmentMap.put("Marketing", com.creative.hfs.hfsbackend.model.enums.Department.MARKETING);
            departmentMap.put("Business Development", com.creative.hfs.hfsbackend.model.enums.Department.BUSINESS_DEVELOPMENT);

            // Look up the department name in the map
            com.creative.hfs.hfsbackend.model.enums.Department department = departmentMap.get(businessUnitName);
            if (department != null) {
                employeeRecord.setDepartmentId(department.getId());
                return;
            }
        }
    }

}
