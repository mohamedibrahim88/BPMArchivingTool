package com.archiving.archivingTool.repository.archiving;

import com.archiving.archivingTool.entity.archiving.ProcessAppsEntity;
import com.archiving.archivingTool.entity.archiving.ProcessAppsGroupsEntity;
import com.archiving.archivingTool.entity.archiving.ServerTypesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProcessAppGroupsRepository extends JpaRepository<ProcessAppsGroupsEntity,Long > {

    @Modifying
    @Transactional
    @Query("DELETE FROM ProcessAppsGroupsEntity s WHERE s.appID = :appID")
    void deleteByAppID(@Param("appID") String appID);

    @Query("SELECT s FROM ProcessAppsGroupsEntity s WHERE s.appID = :appID")
    List<ProcessAppsGroupsEntity> getByAppID(@Param("appID") String appID);
}
