package com.archiving.archivingTool.dto.archiving;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProcessAppGourpsUpdateDto {
  private String appID;
  private List<String> assignedGroups;
  private List<String> assignedUsers;

}
