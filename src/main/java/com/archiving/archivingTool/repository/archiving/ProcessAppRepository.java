package com.archiving.archivingTool.repository.archiving;

import com.archiving.archivingTool.entity.archiving.ProcessAppsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessAppRepository extends JpaRepository<ProcessAppsEntity,Long > {

}
