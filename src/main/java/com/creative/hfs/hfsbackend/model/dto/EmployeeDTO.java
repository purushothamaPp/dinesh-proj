package com.creative.hfs.hfsbackend.model.dto;



import com.creative.hfs.hfsbackend.model.entity.Department;
import com.creative.hfs.hfsbackend.model.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;
import java.util.List;


@Data
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private Integer employeeId;
    private String firstName;
    private String lastName;
    private RoleDTO role;
    private DepartmentDTO department;
    private Integer departmentId; // Assuming departmentId exists in Employee
    private String departmentName; //
    private Employee manager;
    private Employee buHead;
    private String status;
    private List<DepartmentDTO> departments;


    // Add a constructor with the required signature
    public EmployeeDTO(Integer employeeId, String firstName, String lastName) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;



    }

    public EmployeeDTO(Integer employeeId, String firstName, String lastName, Department department, Integer departmentId, String departmentName) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = new DepartmentDTO(departmentId, departmentName); // Assuming DepartmentDTO constructor accepts departmentId and departmentName
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        // Initialize other fields as needed
    }
}
