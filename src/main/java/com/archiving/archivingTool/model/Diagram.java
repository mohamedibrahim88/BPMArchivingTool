package com.archiving.archivingTool.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Diagram {
    @JsonProperty("processAppID")
    String processAppID;
    String milestone;
    List<Step> step;
}
