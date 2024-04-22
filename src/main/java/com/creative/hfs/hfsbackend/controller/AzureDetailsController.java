package com.creative.hfs.hfsbackend.controller;
import com.creative.hfs.hfsbackend.model.entity.AzureDetails;
import com.creative.hfs.hfsbackend.service.AzureDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/azureDetails")
@CrossOrigin("*")
public class AzureDetailsController {

    @Autowired
    private AzureDetailsService azureDetailsService;

    @GetMapping("/{employeeId}")
    public AzureDetails getEmployeeDetails(@PathVariable Integer employeeId) {
        return azureDetailsService.getEmployeeDetails(employeeId);
    }

}
