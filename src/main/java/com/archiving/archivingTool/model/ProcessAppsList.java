package com.archiving.archivingTool.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProcessAppsList {
    String shortName;
    String name;
    String description;
    String richDescription;
    String lastModifiedBy;
    String defaultVersion;
    @JsonProperty("ID")
    String ID;
    String lastModified_on;
    List<InstalledSnapshots> installedSnapshots;

}
