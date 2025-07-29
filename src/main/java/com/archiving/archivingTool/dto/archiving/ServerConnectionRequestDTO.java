package com.archiving.archivingTool.dto.archiving;

import lombok.Data;

@Data
public class ServerConnectionRequestDTO {
    private String host;         // e.g., baw.domain.com
    private int port;            // e.g., 9443
    private String contextPath;  // e.g., /baw
    private String username;
    private String password;
}
