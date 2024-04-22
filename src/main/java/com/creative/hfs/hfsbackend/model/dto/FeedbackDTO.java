package com.creative.hfs.hfsbackend.model.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class FeedbackDTO {
	private Integer feedbackId;
	private Integer creatorId;
	private Integer modifiedBy;
	private Integer assignedManagerId;
	private String areas;
	private String concerns;
	private String remarks;
	private String expressions;
	private LocalDateTime dateCreated;
	private LocalDateTime lastStatusChangeDate;
	private Integer employeeId;
	private String employeeBusinessUnit;
	private String creatorName;
	private String managerName;
	private String employeeName;
	private String modifiedByName;
	private String departmentName;
	private Integer creatorRole;
	private String status;
	private List<ActionItemDTO> actionItems;


}
