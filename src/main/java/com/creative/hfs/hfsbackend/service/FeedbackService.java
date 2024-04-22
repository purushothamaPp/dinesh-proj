package com.creative.hfs.hfsbackend.service;



import com.creative.hfs.hfsbackend.auth.JwtUtil;
import com.creative.hfs.hfsbackend.exceptions.ResourceNotFoundException;
import com.creative.hfs.hfsbackend.model.dto.ActionItemDTO;
import com.creative.hfs.hfsbackend.model.dto.FeedbackDTO;
import com.creative.hfs.hfsbackend.model.entity.*;
import com.creative.hfs.hfsbackend.model.mapper.ActionItemMapper;
import com.creative.hfs.hfsbackend.model.mapper.FeedbackMapper;
import com.creative.hfs.hfsbackend.repository.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FeedbackService {

	@Autowired
	private FeedbackRepository feedbackRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private FeedbackMapper feedbackMapper;


	@Autowired
	private ActionItemRepository actionItemRepository;

	@Autowired
	private ActionItemMapper actionItemMapper;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private EmployeeRecordRepository employeeRecordRepository;

	@Autowired
	private HrbpDepartmentMappingRepository hrbpDepartmentMappingRepository;


	public FeedbackDTO createFeedback(FeedbackDTO feedbackDto) {
		LocalDateTime now = LocalDateTime.now();
		int assignedManagerId;

		List<Integer> loggedInUserDepartmentIds = getDepartmentIdsForEmployee(getLoggedInUser().getEmployeeId());

		// Check if the logged-in user has role 300
		if (getEmployeeRole(getLoggedInUser().getEmployeeId()) == 300) {
			assignedManagerId = getLoggedInUser().getEmployeeId(); // Set assigned manager ID to the logged-in user's ID
		} else {
			// Retrieve the manager associated with the creator ID from the database
			Employee manager = employeeRepository.findById(feedbackDto.getCreatorId())
					.orElseThrow(() -> new RuntimeException("Employee not found")).getManager();
			assignedManagerId = manager.getEmployeeId();
		}

		// Create the feedback entity
		Feedback feedback = feedbackMapper.toEntity(feedbackDto);

		// Create ActionItems and associate them with the feedback
		List<ActionItem> actionItems = feedbackMapper.toEntityList(feedbackDto.getActionItems());
		actionItems.forEach(actionItem -> actionItem.setFeedback(feedback));
		feedback.setActionItems(actionItems);

		feedback.setDateCreated(now);
		feedback.setAssignedManagerId(assignedManagerId);

		//update the status based on action items
		updateFeedbackStatus(feedback);


		// Save the feedback entity
		Feedback savedFeedback = feedbackRepository.save(feedback);

		return feedbackMapper.toDto(savedFeedback);
	}

	public List<Integer> getMatchingDepartmentIdsForEmployeeId(Integer employeeId) {
		int loggedInUserRoleId = getEmployeeRole(getLoggedInUser().getEmployeeId());

		if (loggedInUserRoleId != 100) {
			// Return all records irrespective of department
			return hrbpDepartmentMappingRepository.findAllDepartmentIds(); // Implement this method in your repository
		} else {
			// Return department ids based on employee id
			return hrbpDepartmentMappingRepository.findDepartmentIdsByEmployeeId(employeeId);
		}
	}



	public FeedbackDTO updateFeedback(Integer feedbackId, FeedbackDTO feedbackDto) {
		log.info("updateFeedback(-) started");
		Feedback existingFeedback = feedbackRepository.findById(feedbackId)
				.orElseThrow(() -> new ResourceNotFoundException("Feedback not found with ID: " + feedbackId));
		// Update fields of existing feedback
		existingFeedback.setConcerns(feedbackDto.getConcerns());
		existingFeedback.setAssignedManagerId(feedbackDto.getAssignedManagerId());
		existingFeedback.setRemarks(feedbackDto.getRemarks());
		existingFeedback.setAreas(feedbackDto.getAreas());
		existingFeedback.setExpressions(feedbackDto.getExpressions());
		existingFeedback.setAssignedManagerId(feedbackDto.getAssignedManagerId());
		existingFeedback.setModifiedBy(getLoggedInUser().getEmployeeId());
		existingFeedback.setLastStatusChangeDate(LocalDateTime.now());

		// Update Action Items
		List<ActionItemDTO> updatedActionItemDTOs = feedbackDto.getActionItems();
		List<ActionItem> updatedActionItems = feedbackMapper.toEntityList(updatedActionItemDTOs);
		updatedActionItems.forEach(actionItem -> actionItem.setFeedback(existingFeedback));
		existingFeedback.setActionItems(updatedActionItems);

		//update the status based on action items
		updateFeedbackStatus(existingFeedback);

		// Save the updated feedback
		Feedback savedFeedback = feedbackRepository.save(existingFeedback);
		// Convert the saved feedback to DTO and return
		return feedbackMapper.toDto(savedFeedback);
	}


	public List<FeedbackDTO> getAllFeedbacksWithEmployeeDetails() {
		int loggedInUserRoleId = getEmployeeRole(getLoggedInUser().getEmployeeId());
		List<Feedback> feedbackList;


		if (loggedInUserRoleId == 100) {
			// Get department ids of the logged-in user
			List<Integer> loggedInUserDepartmentIds = getDepartmentIdsForEmployee(getLoggedInUser().getEmployeeId());

			// Get feedbacks excluding creator IDs with roles 200 and 300 and not belonging to departments shared by the logged-in user
			feedbackList = feedbackRepository.findAll().stream()
					.filter(feedback -> {
						int creatorRoleId = getEmployeeRole(feedback.getCreatorId());
						if (creatorRoleId == 100) {
							// Filter feedbacks created by role 100 and sharing at least one department with the logged-in user
							List<Integer> creatorDepartmentIds = getDepartmentIdsForEmployee(feedback.getCreatorId());
							EmployeeRecord taggedUserDepartment = getEmployeeDept(feedback.getEmployeeId());
							boolean creatorSharesDepartment = !Collections.disjoint(loggedInUserDepartmentIds, creatorDepartmentIds);
							boolean loggedInUserHasTaggedDepartment = loggedInUserDepartmentIds.contains(taggedUserDepartment.getDepartmentId());
							return creatorSharesDepartment && loggedInUserHasTaggedDepartment;
						} else {
							// Filter feedbacks created by roles other than 100, 200, and 300
							return false;
						}
					})
					.collect(Collectors.toList());
			

		} else if (loggedInUserRoleId == 200) {
			// Get feedbacks excluding creator IDs with role 300
			feedbackList = feedbackRepository.findAll().stream()
					.filter(feedback -> {
						int creatorRoleId = getEmployeeRole(feedback.getCreatorId());
						return creatorRoleId != 300;
					})
					.collect(Collectors.toList());
		} else {
			// Get all feedbacks for other roles
			feedbackList = feedbackRepository.findAll();
		}

		return feedbackList.stream()
				.map(this::mapFeedbackToDTOWithEmployeeDetails)
				.collect(Collectors.toList());
	}

	private EmployeeRecord getEmployeeDept(Integer employeeId) {
		return employeeRecordRepository.findByEmployeeId(employeeId);
	}


	public List<Integer> getDepartmentIdsForEmployee(int employeeId) {
		// Assuming you have a method in your repository to retrieve department ids for an employee
		List<Integer> departmentIds = hrbpDepartmentMappingRepository.findDepartmentIdsByEmployeeId(employeeId);
		return departmentIds;
	}








	public List<FeedbackDTO> getFeedbacksByDepartment(String departmentName) {
		List<Feedback> feedbacks = feedbackRepository.findAll();
		return feedbacks.stream()
				.filter(feedback -> isCreatorInDepartment(feedback.getCreatorId(), departmentName))
				.map(this::mapFeedbackToDTOWithEmployeeDetails)
				.collect(Collectors.toList());
	}


	public List<FeedbackDTO> getFeedbacks(Integer creatorId) {
		log.info("getFeedbacks(-) started");
		List<Feedback> feedbacks = feedbackRepository.findByCreatorId(creatorId);
		List<FeedbackDTO> feedbackDTOs = feedbackMapper.toDTOList(feedbacks);

		// Fetch associated ActionItems for each Feedback
		for (FeedbackDTO feedbackDTO : feedbackDTOs) {
			List<ActionItem> actionItems = actionItemRepository.findByFeedbackId(feedbackDTO.getFeedbackId());
			List<ActionItemDTO> actionItemDTOs = actionItemMapper.toDTOList(actionItems);
			feedbackDTO.setActionItems(actionItemDTOs);
		}

		log.info("getFeedbacks(-) completed");
		return feedbackDTOs;
	}


	public List<FeedbackDTO> getFeedbacksForManager(Integer assignedManagerId) {
		log.info("getFeedbacks(-) started");
		List<Feedback> feedbacks = feedbackRepository.findByAssignedManagerId(assignedManagerId);
		List<FeedbackDTO> feedbackDTOs = feedbackMapper.toDTOList(feedbacks);

		// Fetch associated ActionItems for each Feedback
		for (FeedbackDTO feedbackDTO : feedbackDTOs) {
			List<ActionItem> actionItems = actionItemRepository.findByFeedbackId(feedbackDTO.getFeedbackId());
			List<ActionItemDTO> actionItemDTOs = actionItemMapper.toDTOList(actionItems);
			feedbackDTO.setActionItems(actionItemDTOs);
		}

		log.info("getFeedbacks(-) completed");
		return feedbackDTOs;
	}


	public FeedbackDTO getFeedbackById(Integer feedbackId) {
		Feedback feedback = feedbackRepository.findById(feedbackId)
				.orElseThrow(() -> new ResourceNotFoundException("Feedback not found with id: " + feedbackId));

		EmployeeRecord employeeRecord = employeeRecordRepository.findById(feedback.getEmployeeId())
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + feedback.getEmployeeId()));

		FeedbackDTO feedbackDTO = feedbackMapper.toDto(feedback);
		feedbackDTO.setDepartmentName(employeeRecord.getBusinessUnitName());

		return feedbackDTO;
	}


	private FeedbackDTO mapFeedbackToDTOWithEmployeeDetails(Feedback feedback) {
		FeedbackDTO feedbackDTO = new FeedbackDTO();
		feedbackDTO.setFeedbackId(feedback.getFeedbackId());
		feedbackDTO.setCreatorId(feedback.getCreatorId());
		feedbackDTO.setAssignedManagerId(feedback.getAssignedManagerId());
		feedbackDTO.setConcerns(feedback.getConcerns());
		feedbackDTO.setRemarks(feedback.getRemarks());
		feedbackDTO.setEmployeeId(feedback.getEmployeeId());
		feedbackDTO.setModifiedBy(feedback.getModifiedBy());
		feedbackDTO.setDateCreated(feedback.getDateCreated());
		feedbackDTO.setLastStatusChangeDate(feedback.getLastStatusChangeDate());
		feedbackDTO.setStatus(feedback.getStatus());

		// Fetch and concatenate creator name from employee_master_table using feedback.getCreatorId()
		Employee creatorEmployee = employeeRepository.findById(feedback.getCreatorId()).orElse(null);
		if (creatorEmployee != null) {
			String creatorName = creatorEmployee.getFirstName();
			if (creatorEmployee.getLastName() != null) {
				creatorName += " " + creatorEmployee.getLastName();
			}
			feedbackDTO.setCreatorName(creatorName);
			feedbackDTO.setCreatorRole(creatorEmployee.getRole().getRoleId());
		} else {
			feedbackDTO.setCreatorName("Unknown"); // Handle the case when employee is not found
			feedbackDTO.setCreatorRole(null);
		}

		// Fetch and concatenate manager name from employee_master_table using feedback.getAssignedManagerId()
		Employee managerEmployee = employeeRepository.findById(feedback.getAssignedManagerId()).orElse(null);
		if (managerEmployee != null) {
			String managerName = managerEmployee.getFirstName();
			if (managerEmployee.getLastName() != null) {
				managerName += " " + managerEmployee.getLastName();
			}
			feedbackDTO.setManagerName(managerName);
		} else {
			feedbackDTO.setManagerName("Unknown"); // Handle the case when employee is not found
		}

		// Fetch and concatenate employee name from employee_master_table using feedback.getEmployeeId()
		EmployeeRecord employeeRecord = employeeRecordRepository.findById(feedback.getEmployeeId()).orElse(null);
		if (employeeRecord != null) {
			String employeeName = employeeRecord.getFirstName();
			String employeeBusinessUnit = employeeRecord.getBusinessUnitName();
			if (employeeRecord.getLastName() != null) {
				employeeName += " " + employeeRecord.getLastName();
			}

			if(employeeRecord.getBusinessUnitName() != null){
				employeeBusinessUnit = " " + employeeRecord.getBusinessUnitName();
			}
			feedbackDTO.setEmployeeName(employeeName);
			feedbackDTO.setEmployeeBusinessUnit(employeeBusinessUnit);

		} else {
			feedbackDTO.setEmployeeName("Unknown"); // Handle the case when employee is not found
		}

		// Fetch and concatenate modifier name from employee_master_table using feedback.getModifiedBy()
		// Fetch and concatenate modifier name from employee_master_table using feedback.getModifiedBy()
		if (feedback.getModifiedBy() != null) {
			Employee modifierEmployee = employeeRepository.findById(feedback.getModifiedBy()).orElse(null);
			if (modifierEmployee != null) {
				String modifierName = modifierEmployee.getFirstName();
				if (modifierEmployee.getLastName() != null) {
					modifierName += " " + modifierEmployee.getLastName();
				}
				feedbackDTO.setModifiedByName(modifierName);
			} else {
				feedbackDTO.setModifiedByName("Unknown"); // Handle the case when employee is not found
			}
		} else {
			feedbackDTO.setModifiedByName("N/A"); // Handle the case when modifiedBy is null
		}


		return feedbackDTO;
	}


	private boolean isCreatorInDepartment(Integer creatorId, String departmentName) {
		Optional<Employee> employeeOptional = employeeRepository.findById(creatorId);
		if (employeeOptional.isPresent()) {
			Employee employee = employeeOptional.get();
			Department department = employee.getDepartment();
			if (department != null) {
				return department.getDepartmentName().equals(departmentName);
			}
		}
		return false;
	}

	private Employee getLoggedInUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserId = authentication.getName(); // Assuming username holds the user ID
		int loggedInUserIdInt = Integer.parseInt(loggedInUserId);

		// Retrieve the logged-in user from the EmployeeRepository
		return employeeRepository.findById(loggedInUserIdInt)
				.orElseThrow(() -> new RuntimeException("Logged-in user not found"));
	}

	private int getEmployeeRole(int employeeId) {
		Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
		if (employeeOptional.isPresent()) {
			Employee employee = employeeOptional.get();
			return employee.getRole().getRoleId(); // Assuming Role is an entity and roleId is a field
		} else {
			throw new ResourceNotFoundException("Employee not found with ID: " + employeeId);
		}


	}


	private void updateFeedbackStatus(Feedback feedback) {
		// Get all action items associated with the feedback
		List<ActionItem> actionItems = feedback.getActionItems();

		if (actionItems.isEmpty()) {
			feedback.setStatus("Open");
		} else {

			boolean hasOpenActionItem = actionItems.stream().anyMatch(actionItem -> actionItem.getStatus().equals("Open"));

			// Update feedback status accordingly
			if (hasOpenActionItem) {
				feedback.setStatus("Open");
			} else {
				feedback.setStatus("Closed");
			}
		}
	}

}
