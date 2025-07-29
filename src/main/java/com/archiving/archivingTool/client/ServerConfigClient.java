package com.archiving.archivingTool.client;

import com.archiving.archivingTool.dto.archiving.ArchivingServerDTO;
import com.archiving.archivingTool.dto.archiving.ServerConnectionRequestDTO;
import com.archiving.archivingTool.entity.archiving.ArchivingServersEntity;
import com.archiving.archivingTool.mapper.ServerConfigMapper;
import com.archiving.archivingTool.repository.archiving.ServerConfigRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Component
public class ServerConfigClient {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ServerConfigRepository repository;

//    @Autowired
//    private JdbcTemplate jdbcTemplate;

    private PlatformTransactionManager archivingTransactionManager;

    @Transactional("archivingTransactionManager")
    public ResponseEntity<String> addServer(ArchivingServerDTO dto) {
        ArchivingServersEntity server = ServerConfigMapper.INSTANCE.fromArchivingServerDtoToEntity(dto);

        if (server == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to map DTO to ProcessApps entity");
        } else {

            System.out.println("Entity: " + server);

            repository.save(server);
            repository.flush();

            return ResponseEntity.ok("Server Added");
        }
    }


    @Transactional("archivingTransactionManager")
    public ArchivingServersEntity updateServer(ArchivingServerDTO dto) {
        ArchivingServersEntity archivingServersEntity = repository.findById(dto.getID()).orElseThrow(()->
                new EntityNotFoundException("Server with ID"+ dto.getID()+"not found"));
        ArchivingServersEntity server = ServerConfigMapper.INSTANCE.fromArchivingServerDtoToEntity(dto);

            repository.save(server);
            repository.flush();

            return archivingServersEntity;
        }


    @Transactional("archivingTransactionManager")
    public void deleteServer(Long id) {
        if (!repository.existsById(id)){

            throw new EntityNotFoundException("Server with ID "+id+"not found");

        }else
            repository.deleteById(id);
    }



         public ResponseEntity<String> testConnection(ServerConnectionRequestDTO request) {
        try {
            String baseUrl = String.format("https://%s:%d%s", request.getHost(), request.getPort(), request.getContextPath());
            URI testUri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                    .path("/authenticated/identity") // or use "/rest/bpm/wle/v1" to hit root
                    .build()
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(request.getUsername(), request.getPassword());
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            headers.set("Cookie", "LtpaToken2=...; JSESSIONID=...");
            headers.set("BPMCSRFToken", "<token_value>");
            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    testUri,
                    HttpMethod.GET
                    , entity
                    , String.class);

            return response;
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed: " + e.getMessage());
        }
    }


    }



