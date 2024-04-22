package com.creative.hfs.hfsbackend.model.dto;


import lombok.*;

@Data
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class EmployeeRecordDTO {
    private Integer employeeId;
    private String firstName;
    private String lastName;
    private String designation;
    private String businessUnitName;
    private String grade;
    private String status;
    private String departmentName;
    private Integer departmentId;
}
