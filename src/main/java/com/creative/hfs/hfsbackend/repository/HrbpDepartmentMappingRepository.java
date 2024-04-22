package com.creative.hfs.hfsbackend.repository;

import com.creative.hfs.hfsbackend.model.entity.Employee;
import com.creative.hfs.hfsbackend.model.entity.HrbpDepartmentMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HrbpDepartmentMappingRepository extends JpaRepository<HrbpDepartmentMapping, Integer> {
    List<HrbpDepartmentMapping> findByEmployeeEmployeeId(Integer employeeId);

    void deleteByEmployee(Employee existingEmployee);

    @Query("SELECT h.department.id FROM HrbpDepartmentMapping h WHERE h.employee.id = :employeeId")
    List<Integer> findDepartmentIdsByEmployeeId(int employeeId);

    @Query("SELECT DISTINCT h.department.id FROM HrbpDepartmentMapping h")
    List<Integer> findAllDepartmentIds();


}
