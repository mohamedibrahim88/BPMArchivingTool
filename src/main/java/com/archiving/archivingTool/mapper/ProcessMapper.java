package com.archiving.archivingTool.mapper;

import com.archiving.archivingTool.dto.archiving.ProcessConfigDto;
import com.archiving.archivingTool.entity.archiving.ProcessAppsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProcessMapper {
    ProcessMapper INSTANCE = Mappers.getMapper(ProcessMapper.class);
    @Mapping(source = "appID", target = "appID")
    @Mapping(source = "acronym", target = "acronym")
    @Mapping(source = "retentionStartDate", target = "retentionStartDate")
    @Mapping(source = "configured", target = "configured")
    ProcessConfigDto fromProcessAppEntityToDto(ProcessAppsEntity processApps);

    @Mapping(source = "appID", target = "appID")
    @Mapping(source = "acronym", target = "acronym")
    @Mapping(source = "retentionStartDate", target = "retentionStartDate")
    @Mapping(source = "configured", target = "configured")
    ProcessAppsEntity fromProcessAppDtoToEntity(ProcessConfigDto processConfigDto);

//    private  Long ID;
//    private String snapshotID;
//    private String branchID;
//    private String acronym;
//    private String isActive;
//    private String activeSince;
//    private String createdOn


}
