package com.archiving.archivingTool.repository.archiving;

import com.archiving.archivingTool.entity.archiving.ProcessApps;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProcessAppRepository extends JpaRepository<ProcessApps,String > {
}
