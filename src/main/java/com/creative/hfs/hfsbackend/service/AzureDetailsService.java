package com.creative.hfs.hfsbackend.service;

import com.creative.hfs.hfsbackend.model.entity.AzureDetails;
import com.creative.hfs.hfsbackend.repository.AzureDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AzureDetailsService {
    @Autowired
    private AzureDetailsRepository azureDetailsRepo;

    public AzureDetails getEmployeeDetails(Integer employeeId) {
        return azureDetailsRepo.findByEmployeeId(employeeId);
    }
}
