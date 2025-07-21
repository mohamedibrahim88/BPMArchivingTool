package com.archiving.archivingTool.repository.archiving;

import com.archiving.archivingTool.entity.archiving.SnapshotsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface SnapshotsRepository extends JpaRepository<SnapshotsEntity,Long> {
}
