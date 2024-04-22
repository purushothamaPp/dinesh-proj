package com.creative.hfs.hfsbackend.repository;

import com.creative.hfs.hfsbackend.model.entity.AzureDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AzureDetailsRepository extends JpaRepository<AzureDetails, Integer> {
    AzureDetails findByEmployeeId(Integer employeeId);
}
