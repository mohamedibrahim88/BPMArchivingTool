package com.archiving.archivingTool.mapper;

import com.archiving.archivingTool.dto.archiving.SnapshotDto;
import com.archiving.archivingTool.entity.archiving.Snapshots;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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
    SnapshotDto fromSnapshotEntityToDto(Snapshots snapshots);

//    @Mapping(source = "ID", target = "ID")
//    @Mapping(source = "snapshotID", target = "snapshotID")
//    @Mapping(source = "branchID", target = "branchID")
//    @Mapping(source = "acronym", target = "acronym")
//    @Mapping(source = "isActive", target = "isActive")
//    @Mapping(source = "activeSince", target = "activeSince")
//    @Mapping(source = "createdOn", target = "createdOn")
    Snapshots fromSnapshotDtoToEntity(SnapshotDto snapshotDto);

    //    @Mapping(source = "ID", target = "ID")
//    @Mapping(source = "snapshotID", target = "snapshotID")
//    @Mapping(source = "branchID", target = "branchID")
//    @Mapping(source = "acronym", target = "acronym")
//    @Mapping(source = "isActive", target = "isActive")
//    @Mapping(source = "activeSince", target = "activeSince")
//    @Mapping(source = "createdOn", target = "createdOn")
    List<SnapshotDto> fromSnapshotEntityListToDto(List<Snapshots> snapshotsList);

    //    @Mapping(source = "ID", target = "ID")
//    @Mapping(source = "snapshotID", target = "snapshotID")
//    @Mapping(source = "branchID", target = "branchID")
//    @Mapping(source = "acronym", target = "acronym")
//    @Mapping(source = "isActive", target = "isActive")
//    @Mapping(source = "activeSince", target = "activeSince")
//    @Mapping(source = "createdOn", target = "createdOn")
    List<Snapshots> fromSnapshotDtoListToEntity(List<SnapshotDto> snapshotDtoList);
}
