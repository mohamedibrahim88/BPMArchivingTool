package com.archiving.archivingTool.mapper;

import com.archiving.archivingTool.dto.archiving.ProcessAppGourpsUpdateDto;
import com.archiving.archivingTool.dto.archiving.ProcessConfigDto;
import com.archiving.archivingTool.entity.archiving.ProcessAppsEntity;
import com.archiving.archivingTool.entity.archiving.ProcessAppsGroupsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ProcessGroupsMapper {
    ProcessGroupsMapper INSTANCE = Mappers.getMapper(ProcessGroupsMapper.class);
//    @Mapping(source = "appID", target = "processAppID")
//    @Mapping(source = "assignedUsers", target = "userName")
//    @Mapping(source = "assignedGroups", target = "groupName")
    ProcessAppsGroupsEntity fromProcessAppDtoToProcessGroupsEntity(ProcessConfigDto processConfigDto);

    default List<ProcessAppsGroupsEntity> fromDtoToListEntity(ProcessConfigDto dto) {
        List<ProcessAppsGroupsEntity> entities = new ArrayList<>();
        if (dto.getAssignedGroups() != null) {
            for (String group : dto.getAssignedGroups()) {
                ProcessAppsGroupsEntity entity = fromProcessAppDtoToProcessGroupsEntity(dto);
                entity.setGroupName(group);
                entity.setUserName(null);
                entities.add(entity);
            }
        }
        if (dto.getAssignedUsers() != null) {
            for (String user : dto.getAssignedUsers()) {
                ProcessAppsGroupsEntity entity = fromProcessAppDtoToProcessGroupsEntity(dto);
                entity.setUserName(user);
                entity.setGroupName(null);
                entities.add(entity);
            }
        }
        if (dto.getAssignedGroups().isEmpty() && dto.getAssignedUsers().isEmpty()){
            ProcessAppsGroupsEntity entity = fromProcessAppDtoToProcessGroupsEntity(dto);
            entity.setGroupName("SuperAdmin");
            entity.setUserName(null);
            entities.add(entity);
        }
        return entities;
    }

    ProcessAppsGroupsEntity fromProcessAppGroupsUpdateDtoToProcessGroupsEntity(ProcessAppGourpsUpdateDto processConfigDto);

    default List<ProcessAppsGroupsEntity> fromProcessAppGroupsUpdateDtoToListEntity(ProcessAppGourpsUpdateDto dto) {
        List<ProcessAppsGroupsEntity> entities = new ArrayList<>();
        if (dto.getAssignedGroups() != null) {
            for (String group : dto.getAssignedGroups()) {
                ProcessAppsGroupsEntity entity = fromProcessAppGroupsUpdateDtoToProcessGroupsEntity(dto);
                entity.setGroupName(group);
                entity.setUserName(null);
                entities.add(entity);
            }
        }
        if (dto.getAssignedUsers() != null) {
            for (String user : dto.getAssignedUsers()) {
                ProcessAppsGroupsEntity entity = fromProcessAppGroupsUpdateDtoToProcessGroupsEntity(dto);
                entity.setUserName(user);
                entity.setGroupName(null);
                entities.add(entity);
            }
        }
        if (dto.getAssignedGroups().isEmpty() && dto.getAssignedUsers().isEmpty()){
            ProcessAppsGroupsEntity entity = fromProcessAppGroupsUpdateDtoToProcessGroupsEntity(dto);
            entity.setGroupName("SuperAdmin");
            entity.setUserName(null);
            entities.add(entity);
        }
        return entities;
    }
//    private  Long ID;
//    private String snapshotID;
//    private String branchID;
//    private String acronym;
//    private String isActive;
//    private String activeSince;
//    private String createdOn


}
