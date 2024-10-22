package com.archiving.archivingTool.model;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProcessDiagram {

    String processAppID;
    List<Step> step;
}
