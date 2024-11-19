package com.archiving.archivingTool.mapper;

import com.archiving.archivingTool.dto.archiving.ProcessConfigDto;
import com.archiving.archivingTool.dto.archiving.SnapshotDto;
import com.archiving.archivingTool.entity.archiving.ProcessApps;
import com.archiving.archivingTool.entity.archiving.Snapshots;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


import java.util.List;

@Mapper
public interface ProcessMapper {
    ProcessMapper INSTANCE = Mappers.getMapper(ProcessMapper.class);
    @Mapping(source = "appID", target = "appID")
    @Mapping(source = "acronym", target = "acronym")
    @Mapping(source = "retentionStartDate", target = "retentionStartDate")
    @Mapping(source = "configured", target = "configured")
    ProcessConfigDto fromProcessAppEntityToDto(ProcessApps processApps);

    @Mapping(source = "appID", target = "appID")
    @Mapping(source = "acronym", target = "acronym")
    @Mapping(source = "retentionStartDate", target = "retentionStartDate")
    @Mapping(source = "configured", target = "configured")
    ProcessApps fromProcessAppDtoToEntity(ProcessConfigDto processConfigDto);

//    private  Long ID;
//    private String snapshotID;
//    private String branchID;
//    private String acronym;
//    private String isActive;
//    private String activeSince;
//    private String createdOn


}
