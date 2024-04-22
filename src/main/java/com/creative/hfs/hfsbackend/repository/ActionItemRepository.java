package com.creative.hfs.hfsbackend.repository;


import com.creative.hfs.hfsbackend.model.entity.ActionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActionItemRepository extends JpaRepository<ActionItem, Integer> {

    @Query("SELECT ai FROM ActionItem ai WHERE ai.feedback.feedbackId = :feedbackId")
    List<ActionItem> findByFeedbackId(Integer feedbackId);

    // You may need other query methods based on your requirements
}
