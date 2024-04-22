package com.creative.hfs.hfsbackend.util;

public class HqlQueries {

    public static final String FIND_EMPLOYEES_BY_BU_HEAD_ID = "FROM Employee WHERE buHead.id = :employeeId";
    public static final String FIND_EMPLOYEES_BY_MANAGER_ID = "FROM Employee WHERE manager.id = :employeeId";
    public static final String FIND_EMPLOYEES_BY_ROLE_NAME = "FROM Employee WHERE role.roleName = :roleName";
    public static final String FIND_TOP_EMPLOYEE_BY_DEPARTMENT_AND_ROLE = "SELECT e FROM Employee e WHERE e.department.departmentName = :departmentName AND e.role.roleName = :roleName";
    public static final String FIND_ALL_ROLES = "SELECT new com.creative.hfs.hfsbackend.model.dto.RoleDTO(r.roleId, r.roleName) FROM Role r";
    public static final String FIND_ALL_DEPARTMENTS = "SELECT new com.creative.hfs.hfsbackend.model.dto.DepartmentDTO(d.departmentId, d.departmentName) FROM Department d";
//    public static final String FIND_EMPLOYEE_NAMES_BY_ROLE_MANAGER = "SELECT new com.creative.hfs.hfsbackend.model.dto.EmployeeDTO(e.employeeId, e.firstName, e.lastName) FROM Employee e WHERE e.role.roleId = 200";

    public static final String FIND_EMPLOYEE_NAMES_BY_ROLE_MANAGER = "SELECT new com.creative.hfs.hfsbackend.model.dto.EmployeeDTO(e.employeeId, e.firstName, e.lastName, e.department, e.department.departmentId, e.department.departmentName)\n" +
            "FROM Employee e\n" +
            "INNER JOIN Department d ON e.department.departmentId = d.departmentId\n" +
            "WHERE e.role.roleId = 200\n";
    public static final String FIND_EMPLOYEE_NAMES_BY_ROLE_BU_HEAD = "SELECT new com.creative.hfs.hfsbackend.model.dto.EmployeeDTO(e.employeeId, e.firstName, e.lastName) FROM Employee e WHERE e.role.roleId = 300";
}
