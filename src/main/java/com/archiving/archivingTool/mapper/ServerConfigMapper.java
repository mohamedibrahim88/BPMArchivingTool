package com.archiving.archivingTool.mapper;

import com.archiving.archivingTool.dto.archiving.ArchivingServerDTO;
import com.archiving.archivingTool.entity.archiving.ArchivingServersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ServerConfigMapper {
    ServerConfigMapper INSTANCE = Mappers.getMapper(ServerConfigMapper.class);
    @Mapping(source = "ID", target = "ID")
    @Mapping(source = "serverName", target = "serverName")
    @Mapping(source = "serverCode", target = "serverCode")
    @Mapping(source = "serverHostName", target = "serverHostName")
    @Mapping(source = "contextPath", target = "contextPath")
    @Mapping(source = "repositoryName", target = "repositoryName")
    @Mapping(source = "databaseType", target = "databaseType")
    @Mapping(source = "serverPort", target = "serverPort")
    @Mapping(source = "userName", target = "userName")
    @Mapping(source = "userPassword", target = "userPassword")
    @Mapping(source = "maximumParallelTransaction", target = "maximumParallelTransaction")
    @Mapping(source = "useSecureConnection", target = "useSecureConnection")
    ArchivingServersEntity fromArchivingServerDtoToEntity(ArchivingServerDTO archivingServerDTO);
}
