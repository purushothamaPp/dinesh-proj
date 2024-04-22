package com.creative.hfs.hfsbackend.model.mapper;

import com.creative.hfs.hfsbackend.model.dto.EmployeeRecordDTO;
import com.creative.hfs.hfsbackend.model.entity.EmployeeRecord;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeRecordMapper {
    private final ModelMapper modelMapper;

    public EmployeeRecordMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }



    public EmployeeRecordDTO toDto(EmployeeRecord employeeRecord) {
        return modelMapper.map(employeeRecord, EmployeeRecordDTO.class);
    }

    public List<EmployeeRecordDTO> toDtoList(List<EmployeeRecord> employeeRecords) {
        return employeeRecords.stream()
                .map(employeeRecord -> modelMapper.map(employeeRecord, EmployeeRecordDTO.class))
                .collect(Collectors.toList());
    }


    public EmployeeRecord toEntity(EmployeeRecordDTO employeeRecordDTO) {
        return modelMapper.map(employeeRecordDTO, EmployeeRecord.class);
    }
}
