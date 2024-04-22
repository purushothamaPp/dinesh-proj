package com.creative.hfs.hfsbackend.model.mapper;

import com.creative.hfs.hfsbackend.model.dto.ActionItemDTO;
import com.creative.hfs.hfsbackend.model.entity.ActionItem;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ActionItemMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public ActionItemMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ActionItemDTO toDTO(ActionItem actionItem) {
        // Map ActionItem entity to ActionItemDTO using ModelMapper
        return modelMapper.map(actionItem, ActionItemDTO.class);
    }

    public List<ActionItemDTO> toDTOList(List<ActionItem> actionItems) {
        // Map list of ActionItem entities to list of ActionItemDTOs using ModelMapper
        return actionItems.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
