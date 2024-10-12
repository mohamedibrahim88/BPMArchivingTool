package com.archiving.archivingTool.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TerminatedInstanceDetails {
    List<FailedOperations> failedOperations;
    List<ProcessDetails> processDetails;
    String succeeded;
    String failed;
}
