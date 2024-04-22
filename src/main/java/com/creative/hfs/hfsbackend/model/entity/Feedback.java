package com.creative.hfs.hfsbackend.model.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "employee_feedback")
public class Feedback {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "feedback_id")
	private Integer feedbackId;

	// Employee ID of the person providing feedback

	@Column(name = "creator_id")
	private Integer creatorId;

	@Column(name = "modified_by")
	private Integer modifiedBy;

	// Manager responsible for addressing the feedback
	@Column(name = "assigned_manager_id")
	private Integer assignedManagerId;

	@Column(name = "areas")
	private String areas;

	@Column(name = "concerns")
	private String concerns;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "expressions")
	private String expressions;


//	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@Column(name = "created_on")
	private LocalDateTime dateCreated;

//	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@Column(name = "updated_on")
	private LocalDateTime lastStatusChangeDate;

	@Column(name="employee_id")
	private Integer employeeId;

	@OneToMany(mappedBy = "feedback", cascade = CascadeType.ALL)
	private List<ActionItem> actionItems;

	@Column(name = "status")
	private String status;

}
