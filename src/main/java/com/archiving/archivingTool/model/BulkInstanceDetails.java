package com.archiving.archivingTool.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BulkInstanceDetails {

    List<ProcessDetails> processDetails;

}
