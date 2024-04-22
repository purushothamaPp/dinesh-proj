package com.creative.hfs.hfsbackend.repository;


import com.creative.hfs.hfsbackend.model.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {



	List<Feedback> findByCreatorId(Integer creatorId);



	List<Feedback> findByAssignedManagerId(Integer assignedManagerId);



	Feedback findByFeedbackId(Integer feedbackId);






}
