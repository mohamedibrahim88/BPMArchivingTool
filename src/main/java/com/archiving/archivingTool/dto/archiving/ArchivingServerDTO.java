package com.archiving.archivingTool.dto.archiving;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArchivingServerDTO {

    private Long ID;

    private String serverName;

    private String serverCode;

    private String serverHostName;

    private String contextPath;

    private String repositoryName;

    private String databaseType;

    private String serverPort;

    private String userName;

    private String userPassword;

    private String maximumParallelTransaction;

    private int useSecureConnection;
}