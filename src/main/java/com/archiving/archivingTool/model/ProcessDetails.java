package com.archiving.archivingTool.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProcessDetails {

    String creationTime;
    String description;
    String richDescription;
    String executionState;
    String state;
    String lastModificationTime;
    String name;
    String piid;
    String caseFolderID;
    String caseFolderServerName;
    String processTemplateID;
    String processTemplateName;
    String processAppName;
    String processAppAcronym;
    String processAppID;
    String snapshotName;
    String snapshotID;
    String branchID;
    String branchName;
    String snapshotTip;
    String startingDocumentServerName;
    String dueDate;
    List<String> comments;
    List<String> documents;
    String actionDetails;
    String relationship;
    String actions;
    String starterId;

}
