package com.creative.hfs.hfsbackend.service;

import com.creative.hfs.hfsbackend.model.dto.EmployeeRecordDTO;
import com.creative.hfs.hfsbackend.model.entity.EmployeeRecord;
import com.creative.hfs.hfsbackend.model.mapper.EmployeeRecordMapper;
import com.creative.hfs.hfsbackend.repository.EmployeeRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeRecordService {


    @Autowired
    private EmployeeRecordRepository employeeRecordRepository;

    @Autowired
    private EmployeeRecordMapper employeeRecordMapper;

    public EmployeeRecordDTO getEmployeeRecordById(Integer employeeId) {
        EmployeeRecord employeeRecord = employeeRecordRepository.findByEmployeeId(employeeId);
        if (employeeRecord == null) {
            throw new RuntimeException("Employee Record not found with ID: " + employeeId);
        }
        return employeeRecordMapper.toDto(employeeRecord);
    }

    public List<EmployeeRecordDTO> getAllEmployeeRecords() {
        List<EmployeeRecord> employeeRecords = employeeRecordRepository.findAll();
        return employeeRecordMapper.toDtoList(employeeRecords);
    }

    public EmployeeRecord saveEmployeeRecord(EmployeeRecord employeeRecord) {
        return employeeRecordRepository.save(employeeRecord);
    }


}
