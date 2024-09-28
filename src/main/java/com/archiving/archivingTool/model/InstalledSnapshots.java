package com.archiving.archivingTool.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InstalledSnapshots {

    String name;
    String acronym;
    String active;
    //String activeSince;
    String createdOn;
    boolean snapshotTip;
    String branchID;
    String branchName;
    @JsonProperty("ID")
    String ID;

}
