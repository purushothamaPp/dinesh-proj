package com.creative.hfs.hfsbackend.repository;

import com.creative.hfs.hfsbackend.model.dto.DepartmentDTO;
import com.creative.hfs.hfsbackend.model.dto.EmployeeDTO;
import com.creative.hfs.hfsbackend.model.dto.RoleDTO;
import com.creative.hfs.hfsbackend.model.entity.Employee;
import com.creative.hfs.hfsbackend.util.HqlQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    boolean existsByEmployeeId(Integer employeeId);

    Optional<Employee> findById(Integer employeeId);

    @Query(HqlQueries.FIND_EMPLOYEES_BY_BU_HEAD_ID)
    List<Employee> findEmployeesByBuHeadId(Integer employeeId);

    @Query(HqlQueries.FIND_EMPLOYEES_BY_MANAGER_ID)
    List<Employee> findEmployeesByManagerId(Integer employeeId);

    @Query(HqlQueries.FIND_EMPLOYEES_BY_ROLE_NAME)
    List<Employee> findByRole_RoleName(String roleName);

    @Query(HqlQueries.FIND_TOP_EMPLOYEE_BY_DEPARTMENT_AND_ROLE)
    Employee findTopByDepartment_DepartmentNameAndRole_RoleName(String departmentName, String roleName);

    @Query(HqlQueries.FIND_ALL_ROLES)
    List<RoleDTO> findAllRoles();

    @Query(HqlQueries.FIND_ALL_DEPARTMENTS)
    List<DepartmentDTO> findAllDepartments();

    @Query(HqlQueries.FIND_EMPLOYEE_NAMES_BY_ROLE_MANAGER)
    List<EmployeeDTO> findEmployeesBy_MANAGER_ROLE();

    @Query(HqlQueries.FIND_EMPLOYEE_NAMES_BY_ROLE_BU_HEAD)
    List<EmployeeDTO> findEmployeesBy_BU_HEAD_ROLE();
}
