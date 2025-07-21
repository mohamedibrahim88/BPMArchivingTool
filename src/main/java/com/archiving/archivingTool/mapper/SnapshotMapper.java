package com.archiving.archivingTool.mapper;

import com.archiving.archivingTool.dto.archiving.SnapshotDto;
import com.archiving.archivingTool.entity.archiving.SnapshotsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SnapshotMapper {
    SnapshotMapper INSTANCE = Mappers.getMapper(SnapshotMapper.class);


//    @Mapping(source = "ID", target = "ID")
//    @Mapping(source = "snapshotID", target = "snapshotID")
//    @Mapping(source = "branchID", target = "branchID")
//    @Mapping(source = "acronym", target = "acronym")
//    @Mapping(source = "isActive", target = "isActive")
//    @Mapping(source = "activeSince", target = "activeSince")
//    @Mapping(source = "createdOn", target = "createdOn")
    SnapshotDto fromSnapshotEntityToDto(SnapshotsEntity snapshotsEntity);

//    @Mapping(source = "ID", target = "ID")
//    @Mapping(source = "snapshotID", target = "snapshotID")
//    @Mapping(source = "branchID", target = "branchID")
//    @Mapping(source = "acronym", target = "acronym")
//    @Mapping(source = "isActive", target = "isActive")
//    @Mapping(source = "activeSince", target = "activeSince")
//    @Mapping(source = "createdOn", target = "createdOn")
    SnapshotsEntity fromSnapshotDtoToEntity(SnapshotDto snapshotDto);

    //    @Mapping(source = "ID", target = "ID")
//    @Mapping(source = "snapshotID", target = "snapshotID")
//    @Mapping(source = "branchID", target = "branchID")
//    @Mapping(source = "acronym", target = "acronym")
//    @Mapping(source = "isActive", target = "isActive")
//    @Mapping(source = "activeSince", target = "activeSince")
//    @Mapping(source = "createdOn", target = "createdOn")
    List<SnapshotDto> fromSnapshotEntityListToDto(List<SnapshotsEntity> snapshotsEntityList);

    //    @Mapping(source = "ID", target = "ID")
//    @Mapping(source = "snapshotID", target = "snapshotID")
//    @Mapping(source = "branchID", target = "branchID")
//    @Mapping(source = "acronym", target = "acronym")
//    @Mapping(source = "isActive", target = "isActive")
//    @Mapping(source = "activeSince", target = "activeSince")
//    @Mapping(source = "createdOn", target = "createdOn")
    List<SnapshotsEntity> fromSnapshotDtoListToEntity(List<SnapshotDto> snapshotDtoList);
}
