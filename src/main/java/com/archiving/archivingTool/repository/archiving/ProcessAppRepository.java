package com.archiving.archivingTool.repository.archiving;

import com.archiving.archivingTool.entity.archiving.ProcessApps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProcessAppRepository extends JpaRepository<ProcessApps,String > {

}
