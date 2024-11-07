package com.archiving.archivingTool.service;

import com.archiving.archivingTool.entity.archiving.ProcessApps;
import com.archiving.archivingTool.repository.archiving.ProcessAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
@Service
public class ArchivingService {
    private  PlatformTransactionManager archivingTransactionManager;

    @Autowired
    private ProcessAppRepository processAppRepository;

    @Transactional("archivingTransactionManager") // Specify the transaction manager to use
    public ProcessApps createOrUpdateEntity(ProcessApps entity) {
        return processAppRepository.save(entity); // This will affect the primary database only
    }
}
