package com.archiving.archivingTool.dto.archiving;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConnectionStatusResponse {
    private String status;
    private String message;
}