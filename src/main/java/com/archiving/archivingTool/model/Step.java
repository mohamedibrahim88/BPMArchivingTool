package com.archiving.archivingTool.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Step {
    String name;
    String type;
    String activityType;
    @JsonProperty("externalID")
    String externalID;
    String lane;
    @JsonProperty("taskID")
    String taskID;
    @JsonProperty("ID")
    String ID;
}
