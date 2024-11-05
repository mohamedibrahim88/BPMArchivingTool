package com.archiving.archivingTool.dto;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeleteSnapshotDTO {

    String processAcronym;
    List<String> snapshotAcronym;
}
