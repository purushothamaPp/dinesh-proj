package com.creative.hfs.hfsbackend.service;


import com.creative.hfs.hfsbackend.model.dto.DepartmentDTO;
import com.creative.hfs.hfsbackend.model.entity.HrbpDepartmentMapping;
import com.creative.hfs.hfsbackend.repository.HrbpDepartmentMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HrbpDepartmentMappingService {

    @Autowired
    private HrbpDepartmentMappingRepository hrbpDepartmentMappingRepository;

    public List<DepartmentDTO> getDepartmentsByEmployeeId(Integer employeeId) {
        List<HrbpDepartmentMapping> mappings = hrbpDepartmentMappingRepository.findByEmployeeEmployeeId(employeeId);
        return mappings.stream()
                .map(mapping -> {
                    DepartmentDTO dto = new DepartmentDTO();
                    dto.setDepartmentId(mapping.getDepartment().getDepartmentId());
                    dto.setDepartmentName(mapping.getDepartment().getDepartmentName());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
