package com.archiving.archivingTool.dto.bpm;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InstancesListDTO {

    List<String> instancesIDs;

}
