package com.archiving.archivingTool.repository.archiving;

import com.archiving.archivingTool.entity.archiving.ServerTypesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ServerTypesRepository extends JpaRepository<ServerTypesEntity,Long> {

    @Query("SELECT s FROM ServerTypesEntity s WHERE s.serverCode = :serverCode")
    Optional<ServerTypesEntity> findByServerCode(@Param("serverCode") String serverCode);

}
