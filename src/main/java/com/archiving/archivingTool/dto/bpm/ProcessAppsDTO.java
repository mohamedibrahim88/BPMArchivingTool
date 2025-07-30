package com.archiving.archivingTool.dto.bpm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProcessAppsDTO {
    @JsonProperty("shortName")
    String acronym;
    @JsonProperty("name")
    String name;
    String description;
    String richDescription;
    String lastModifiedBy;
    String defaultVersion;
    @JsonProperty("ID")
    String appID;
    String lastModified_on;
}
