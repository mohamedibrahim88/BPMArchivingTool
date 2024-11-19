package com.archiving.archivingTool.dto.archiving;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SnapshotDto {
    private  Long ID;
    private String snapshotID;
    private String branchID;
    private String acronym;
    private String isActive;
    private String activeSince;
    private String createdOn;
}
