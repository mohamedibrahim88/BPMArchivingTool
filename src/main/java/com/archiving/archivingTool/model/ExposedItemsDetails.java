package com.archiving.archivingTool.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExposedItemsDetails {

    String type;
    String itemID;
    String itemReference;
    String processAppID;
    String processAppName;
    String processAppAcronym;
    String snapshotID;
    String snapshotCreatedOn;
    String display;
    String tip;
    String branchID;
    String branchName;
    String startURL;
    String isDefault;
    String isMobileReady;
    @JsonProperty("ID")
    String ID;
}
