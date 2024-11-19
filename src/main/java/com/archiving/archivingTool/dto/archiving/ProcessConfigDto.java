package com.archiving.archivingTool.dto.archiving;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProcessConfigDto {
  private String appID;
  private String acronym;
  private String name;
  private String retentionStartDate;
  @JsonProperty("isConfigured")
  private boolean isConfigured;
}
