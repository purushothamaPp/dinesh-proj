package com.creative.hfs.hfsbackend.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "employee_feedback_action")
public class ActionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "action_id")
    private Integer actionId;

    private String description;

    private Integer actionOwner;

    private String actionOwnerName;

    private String status;

    private String rag;

    private String actionOwnerType;

    private String comments;

    @ManyToOne
    @JoinColumn(name = "feedback_id", nullable = false)
    private Feedback feedback;

    // ... other fields, getters, and setters
}

