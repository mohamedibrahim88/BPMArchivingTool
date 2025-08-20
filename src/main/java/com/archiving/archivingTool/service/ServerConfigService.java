package com.archiving.archivingTool.service;

import com.archiving.archivingTool.client.ServerConfigClient;
import com.archiving.archivingTool.dto.archiving.ArchivingServerDTO;
import com.archiving.archivingTool.dto.archiving.ServerConnectionRequestDTO;
import com.archiving.archivingTool.entity.archiving.ArchivingServersEntity;
import com.archiving.archivingTool.entity.archiving.ProcessAppsEntity;
import com.archiving.archivingTool.mapper.ServerConfigMapper;
import com.archiving.archivingTool.repository.archiving.ProcessAppRepository;
import com.archiving.archivingTool.repository.archiving.ServerConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServerConfigService {
    private  PlatformTransactionManager archivingTransactionManager;
    @Autowired
    private ProcessAppRepository processAppRepository;
    @Autowired
    private ServerConfigRepository serverConfigRepository;
    @Autowired
    private ServerConfigMapper serverConfigMapper;
    @Autowired
    private ServerConfigClient serverConfigClient;
    @Transactional("archivingTransactionManager") // Specify the transaction manager to use
    public ProcessAppsEntity createOrUpdateEntity(ProcessAppsEntity entity) {
        return processAppRepository.save(entity); // This will affect the primary database only
    }

    @Transactional("archivingTransactionManager") // Specify the transaction manager to use
    public ResponseEntity<String> addServer (ArchivingServerDTO archivingServerDTO)
    {
        ArchivingServersEntity server = serverConfigMapper.fromArchivingServerDtoToEntity(archivingServerDTO);
        return serverConfigClient.addServer(archivingServerDTO); // This will affect the primary database only
    }

    @Transactional("archivingTransactionManager") // Specify the transaction manager to use
    public ArchivingServersEntity updateServer (ArchivingServerDTO archivingServerDTO)
    {
        ArchivingServersEntity server = serverConfigMapper.fromArchivingServerDtoToEntity(archivingServerDTO);
        return serverConfigClient.updateServer(archivingServerDTO); // This will affect the primary database only
    }

    @Transactional("archivingTransactionManager") // Specify the transaction manager to use
    public void deleteServer (Long id)
    {
         serverConfigClient.deleteServer(id); // This will affect the primary database only
    }

     public ResponseEntity<String> testConnection (ServerConnectionRequestDTO request){

        return serverConfigClient.testConnection(request);

    }

    public List<ArchivingServerDTO> getServerByServerCode(String serverCode){

        return serverConfigClient.getServerByServerCode(serverCode);
    }
}
