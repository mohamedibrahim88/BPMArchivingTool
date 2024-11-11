package com.archiving.archivingTool.repository.archiving;

import com.archiving.archivingTool.entity.archiving.Snapshots;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstalledSnapshotsRepository extends JpaRepository<Snapshots,String> {
}
