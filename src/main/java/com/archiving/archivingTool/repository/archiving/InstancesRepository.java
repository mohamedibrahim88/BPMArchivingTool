package com.archiving.archivingTool.repository.archiving;

import com.archiving.archivingTool.entity.archiving.InstancesEntity;
import com.archiving.archivingTool.entity.archiving.ProcessAppsEntity;
import com.archiving.archivingTool.entity.archiving.ProcessAppsGroupsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InstancesRepository extends JpaRepository<InstancesEntity,Long > {

    @Query("SELECT s FROM InstancesEntity s WHERE s.piid = :piid")
    InstancesEntity getByPiid(@Param("piid") String piid);
}
