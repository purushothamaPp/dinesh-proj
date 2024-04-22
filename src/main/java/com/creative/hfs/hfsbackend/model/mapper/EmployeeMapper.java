package com.creative.hfs.hfsbackend.model.mapper;



import com.creative.hfs.hfsbackend.model.dto.EmployeeDTO;
import com.creative.hfs.hfsbackend.model.entity.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {
	private final ModelMapper modelMapper;

	public EmployeeMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public Employee toEntity(EmployeeDTO employeeDto) {
		return modelMapper.map(employeeDto, Employee.class);
	}

	public EmployeeDTO toDto(Employee employee) {
		return modelMapper.map(employee, EmployeeDTO.class);
	}

	public List<EmployeeDTO> toDtoList(List<Employee> employees) {
		return employees.stream()
				.map(this::toDto)
				.collect(Collectors.toList());
	}


}
