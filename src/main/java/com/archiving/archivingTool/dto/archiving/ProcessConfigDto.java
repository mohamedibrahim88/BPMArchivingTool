package com.archiving.archivingTool.dto.archiving;

import com.archiving.archivingTool.model.InstalledSnapshots;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class ProcessConfigDto {
    String shortName;
    String name;
    String description;
    String richDescription;
    String lastModifiedBy;
    String defaultVersion;
    String ID;
    String lastModified_on;
}
