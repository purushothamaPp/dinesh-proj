package com.creative.hfs.hfsbackend.model.dto;

import com.creative.hfs.hfsbackend.model.entity.Department;
import com.creative.hfs.hfsbackend.model.entity.Employee;
import com.creative.hfs.hfsbackend.model.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {
    private Integer employeeId;
    private String firstName;
    private String lastName;
    private Role role;
    private List<Department> departments;
    private Employee manager;
    private Employee buHead;
    private String status;

}
