package com.archiving.archivingTool.mapper;

import com.archiving.archivingTool.dto.archiving.ProcessConfigDto;
import com.archiving.archivingTool.entity.archiving.ProcessApps;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

public interface ProcessMapper {
    ProcessMapper INSTANCE = Mappers.getMapper(ProcessMapper.class);
      ProcessConfigDto fromEntityToDto(ProcessApps processApps);
      ProcessApps fromDtoToProcessEntity(ProcessConfigDto processConfigDto);
}
