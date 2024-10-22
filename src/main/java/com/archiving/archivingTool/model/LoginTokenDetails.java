package com.archiving.archivingTool.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginTokenDetails {

    String expiration;
    @JsonProperty("csrf_token")
    String csrfToken;
}
