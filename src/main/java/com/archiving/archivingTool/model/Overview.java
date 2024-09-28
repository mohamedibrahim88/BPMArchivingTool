package com.archiving.archivingTool.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Overview {
    @JsonProperty("Active")
    int Active ;
    @JsonProperty("Total")
    int Total;
    @JsonProperty("Completed")
    int Completed;
    @JsonProperty("Failed")
    int Failed;
    @JsonProperty("Terminated")
    int Terminated;
    @JsonProperty("Did_not_Start")
    int  Did_not_Start;
    @JsonProperty("Suspended")
    int Suspended;

}
