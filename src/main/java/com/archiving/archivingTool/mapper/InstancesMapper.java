package com.archiving.archivingTool.mapper;

import com.archiving.archivingTool.entity.archiving.InstancesEntity;
import com.archiving.archivingTool.model.Processes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface InstancesMapper {
    InstancesMapper INSTANCE = Mappers.getMapper(InstancesMapper.class);
//    @Mapping(source = "appID", target = "appID")
//    @Mapping(source = "acronym", target = "acronym")
//    @Mapping(source = "retentionStartDate", target = "retentionStartDate")
//    @Mapping(source = "configured", target = "configured")
//    ProcessConfigDto fromProcessAppEntityToDto(ProcessAppsEntity processApps);

    @Mapping(source = "piid", target = "piid")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "bpdName", target = "bpdName")
//    @Mapping(source = "snapshotID", target = "snapshotI")
    @Mapping(source = "executionState", target = "executionState")
    @Mapping(source = "lastModificationTime", target = "lastModificationTime")
    @Mapping(source = "data", target = "data")
    List<InstancesEntity> fromInstancesToEntity(List<Processes> instances);

//    private  Long ID;
//    private String snapshotID;
//    private String branchID;
//    private String acronym;
//    private String isActive;
//    private String activeSince;
//    private String createdOn


}
