package com.archiving.archivingTool.repository.archiving;

import com.archiving.archivingTool.entity.archiving.ProcessAppsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProcessAppRepository extends JpaRepository<ProcessAppsEntity,Long > {


    @Query("SELECT s FROM ProcessAppsEntity s WHERE s.appID = :appID")
    List<ProcessAppsEntity> getByAppID(@Param("appID") String appID);
}
