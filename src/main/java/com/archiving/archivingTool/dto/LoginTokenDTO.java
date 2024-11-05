package com.archiving.archivingTool.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginTokenDTO {
    @JsonProperty("refresh-groups")
    String refreshGroups;
    @JsonProperty("requested-lifetime")
    String requestedLifetime;
}
