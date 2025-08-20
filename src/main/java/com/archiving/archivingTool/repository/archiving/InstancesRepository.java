package com.archiving.archivingTool.repository.archiving;

import com.archiving.archivingTool.entity.archiving.InstancesEntity;
import com.archiving.archivingTool.entity.archiving.ProcessAppsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstancesRepository extends JpaRepository<InstancesEntity,Long > {

}
