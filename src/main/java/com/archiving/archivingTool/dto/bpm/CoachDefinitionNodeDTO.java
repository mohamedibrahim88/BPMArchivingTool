package com.archiving.archivingTool.dto.bpm;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CoachDefinitionNodeDTO {
    private String label;
    private String viewUUID;
    private String controlType;
    private String binding;
    private List<CoachDefinitionNodeDTO> children;
}
