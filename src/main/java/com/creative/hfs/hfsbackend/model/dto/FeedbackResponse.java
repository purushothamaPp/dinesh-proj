package com.creative.hfs.hfsbackend.model.dto;

import com.creative.hfs.hfsbackend.exceptions.FeedbackError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackResponse {

	private FeedbackDTO feedbackDTO;
	private FeedbackError feedbackerror;

}
