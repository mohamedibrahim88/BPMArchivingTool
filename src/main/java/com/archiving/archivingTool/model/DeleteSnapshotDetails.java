package com.archiving.archivingTool.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DeleteSnapshotDetails {
    @JsonProperty("error_number")
    String errorNumber;
    @JsonProperty("error_message")
    String errorMessage;
    @JsonProperty("error_message_parameters")
    List<String> errorMessageParameters;
    @JsonProperty("description")
    String description;
}
