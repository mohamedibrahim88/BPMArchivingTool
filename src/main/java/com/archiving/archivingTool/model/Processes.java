package com.archiving.archivingTool.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Processes {

    String piid;
    String name;
    String bpdName;
    String snapshotID;
    String dueDate;
    String executionState;
    String lastModificationTime;

}
