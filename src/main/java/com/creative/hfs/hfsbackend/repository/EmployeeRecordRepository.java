package com.creative.hfs.hfsbackend.repository;

import com.creative.hfs.hfsbackend.model.entity.EmployeeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeRecordRepository extends JpaRepository<EmployeeRecord, Integer> {
    EmployeeRecord findByEmployeeId(Integer employeeId);

}
