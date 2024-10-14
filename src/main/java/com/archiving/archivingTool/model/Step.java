package com.archiving.archivingTool.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Step {
    String name;
    String type;
    String activityType;
    String externalID;
    String diagram;
    String lane;
    @JsonProperty("taskID")
    String taskID;
    @JsonProperty("ID")
    String ID;
}
