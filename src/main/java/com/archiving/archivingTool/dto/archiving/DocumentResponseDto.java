package com.archiving.archivingTool.dto.archiving;


import lombok.Data;

@Data
public class DocumentResponseDto {
    private String id;
    private String name;
    private Integer version;
    private String creator;
    private String dateCreated;
    private String instanceId;

    private String downloadUrl;

}
