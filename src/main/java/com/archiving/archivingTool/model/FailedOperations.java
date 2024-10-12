package com.archiving.archivingTool.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FailedOperations {

    String instanceId;
    String errorNumber;
    String errorMessage;
}
