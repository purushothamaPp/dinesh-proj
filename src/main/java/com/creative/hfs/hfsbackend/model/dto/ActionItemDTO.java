package com.creative.hfs.hfsbackend.model.dto;

import lombok.*;

@Data
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class ActionItemDTO {
    private Integer actionId;
    private String description;
    private Integer actionOwner;
    private String actionOwnerName;
    private String status;
    private String rag;
    private String actionOwnerType;
    private String comments;
}
