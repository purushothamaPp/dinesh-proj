package com.creative.hfs.hfsbackend.controller;


import com.creative.hfs.hfsbackend.exceptions.ResourceNotFoundException;
import com.creative.hfs.hfsbackend.model.dto.EmployeeDTO;
import com.creative.hfs.hfsbackend.model.dto.FeedbackDTO;
import com.creative.hfs.hfsbackend.model.dto.RoleDTO;
import com.creative.hfs.hfsbackend.model.enums.UserRole;
import com.creative.hfs.hfsbackend.service.EmployeeService;
import com.creative.hfs.hfsbackend.service.FeedbackService;
import com.creative.hfs.hfsbackend.util.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Slf4j
@CrossOrigin("*")
public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;

	@Autowired
	private EmployeeService employeeService;




	@PostMapping("/feedback")
	public ResponseEntity<ApiResponse<FeedbackDTO>> createFeedback(@RequestBody FeedbackDTO feedbackDto) {

		try {
			FeedbackDTO savedFeedback = feedbackService.createFeedback(feedbackDto);
			log.info("Created feedback: " + savedFeedback);
			return ResponseEntity.ok(new ApiResponse<>(savedFeedback));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse<>(e.getMessage()));
		}
	}



	@PutMapping("/feedback/{feedbackId}")
	public ResponseEntity<ApiResponse<FeedbackDTO>> updateFeedback(@PathVariable Integer feedbackId, @RequestBody FeedbackDTO feedbackDto) {
		try {
			FeedbackDTO updatedFeedback = feedbackService.updateFeedback(feedbackId, feedbackDto);
			log.info("Updated feedback: " + updatedFeedback);
			return ResponseEntity.ok(new ApiResponse<>(updatedFeedback));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(e.getMessage()));
		}
	}



	@GetMapping("/feedback")
	public ResponseEntity<List<FeedbackDTO>> getAllFeedbacks() {
		log.info("getAllFeedbacks(-) started");
		List<FeedbackDTO> allFeedbacks = feedbackService.getAllFeedbacksWithEmployeeDetails();
		log.info("getAllFeedbacks(-) completed and data is : "+ allFeedbacks);
		return ResponseEntity.ok(allFeedbacks);
	}


	@GetMapping("/feedbackByDepartment/{creatorId}")
	public ResponseEntity<List<FeedbackDTO>> getFeedbacksByDepartment(@PathVariable Integer creatorId) {
		log.info("getFeedbacksByDepartment(-) started: " + creatorId);
		try {
			String creatorDepartmentName = getDepartmentNameFromCreatorId(creatorId);
			List<FeedbackDTO> feedbacks = feedbackService.getFeedbacksByDepartment(creatorDepartmentName);
			log.info("getFeedbacksByDepartment(-) completed");
			return ResponseEntity.ok(feedbacks);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
		}
	}

	@GetMapping("/feedback/{creatorId}")
	public ResponseEntity<List<FeedbackDTO>> getFeedbacks(@PathVariable Integer creatorId) {
		log.info("getFeedbacks(-) started :" + creatorId);
		List<FeedbackDTO> feedbacks = feedbackService.getFeedbacks(creatorId);
		log.info("getFeedbacks(-) completed");
		return ResponseEntity.ok(feedbacks);
	}

	@GetMapping("/feedbackManager/{assignedManagerId}")
	public ResponseEntity<List<FeedbackDTO>> getFeedbacksForManager(@PathVariable Integer assignedManagerId) {
		log.info("getFeedbacks(-) started :" + assignedManagerId);
		List<FeedbackDTO> feedbacks = feedbackService.getFeedbacksForManager(assignedManagerId);
		log.info("getFeedbacks(-) completed");
		return ResponseEntity.ok(feedbacks);
	}

	// New endpoint to fetch feedback based on feedbackId
	@GetMapping("/feedbackById/{feedbackId}")
	public ResponseEntity<ApiResponse<FeedbackDTO>> getFeedbackById(@PathVariable Integer feedbackId) {
		try {
			FeedbackDTO feedback = feedbackService.getFeedbackById(feedbackId);
			return ResponseEntity.ok(new ApiResponse<>(feedback));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(e.getMessage()));
		}
	}

	@GetMapping("/hrbp-tagged-departments/{employeeId}")
	public List<Integer> getMatchingDepartmentIds(@PathVariable Integer employeeId) {
		return feedbackService.getMatchingDepartmentIdsForEmployeeId(employeeId);
	}







	// Helper method to get the department name from creatorId
	private String getDepartmentNameFromCreatorId(int creatorId) {
		Optional<EmployeeDTO> employeeDto = employeeService.findEmployeeDtoById(creatorId);
		if (employeeDto.isEmpty()) {
			throw new ResourceNotFoundException("Employee not found with ID: " + creatorId);
		}
		return employeeDto.get().getDepartmentName();
	}

	}
