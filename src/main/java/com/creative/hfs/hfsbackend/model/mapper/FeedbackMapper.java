package com.creative.hfs.hfsbackend.model.mapper;

import com.creative.hfs.hfsbackend.model.dto.ActionItemDTO;
import com.creative.hfs.hfsbackend.model.dto.FeedbackDTO;
import com.creative.hfs.hfsbackend.model.entity.ActionItem;
import com.creative.hfs.hfsbackend.model.entity.Feedback;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FeedbackMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public FeedbackMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public FeedbackDTO toDto(Feedback feedback) {
        return modelMapper.map(feedback, FeedbackDTO.class);
    }

    public Feedback toEntity(FeedbackDTO feedbackDTO) {
        return modelMapper.map(feedbackDTO, Feedback.class);
    }

    public List<FeedbackDTO> toDTOList(List<Feedback> feedbackList) {
        return feedbackList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ActionItem toEntity(ActionItemDTO actionItemDTO) {
        return modelMapper.map(actionItemDTO, ActionItem.class);
    }

    public ActionItemDTO toDto(ActionItem actionItem) {
        return modelMapper.map(actionItem, ActionItemDTO.class);
    }

    public List<ActionItem> toEntityList(List<ActionItemDTO> actionItemDTOs) {
        return actionItemDTOs.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public List<ActionItemDTO> toDtoList(List<ActionItem> actionItems) {
        return actionItems.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
