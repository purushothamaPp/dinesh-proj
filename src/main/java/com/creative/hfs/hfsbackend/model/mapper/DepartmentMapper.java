package com.creative.hfs.hfsbackend.model.mapper;

import com.creative.hfs.hfsbackend.model.dto.DepartmentDTO;
import com.creative.hfs.hfsbackend.model.entity.Department;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

	private final ModelMapper modelMapper;

	@Autowired
	public DepartmentMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public DepartmentDTO toDto(Department department) {
		// Map Department entity to DepartmentDTO using ModelMapper
		return modelMapper.map(department, DepartmentDTO.class);
	}

	public Department toEntity(DepartmentDTO departmentDto) {
		// Map DepartmentDTO to Department entity using ModelMapper
		return modelMapper.map(departmentDto, Department.class);
	}
}
