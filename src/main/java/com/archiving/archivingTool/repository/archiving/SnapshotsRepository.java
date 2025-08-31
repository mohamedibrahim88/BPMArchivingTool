package com.archiving.archivingTool.repository.archiving;

import com.archiving.archivingTool.entity.archiving.ArchivingServersEntity;
import com.archiving.archivingTool.entity.archiving.SnapshotsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SnapshotsRepository extends JpaRepository<SnapshotsEntity,Long> {

    @Query("select a from SnapshotsEntity a where a.snapshotID = :snapshotID")
    List<SnapshotsEntity> findBySnapshotID(@Param("snapshotID")String snapshotID);
}
