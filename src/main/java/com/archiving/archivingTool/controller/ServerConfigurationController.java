package com.archiving.archivingTool.controller;

import com.archiving.archivingTool.dto.archiving.ArchivingServerDTO;
import com.archiving.archivingTool.dto.archiving.ServerConnectionRequestDTO;
import com.archiving.archivingTool.entity.archiving.ArchivingServersEntity;
import com.archiving.archivingTool.service.ServerConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/server")
public class ServerConfigurationController {
    @Autowired
    private ServerConfigService archivingService;

    @PostMapping("/add")
    public ResponseEntity<String> createServer(@RequestBody ArchivingServerDTO dto) {
        return archivingService.addServer(dto);
    }
    @PutMapping("/update")
    public ArchivingServersEntity updateServer(@RequestBody ArchivingServerDTO dto) {
        return archivingService.updateServer(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteServer(@PathVariable Long id) {
         archivingService.deleteServer(id);
         return ResponseEntity.noContent().build();
    }

    @PostMapping("/test-srv")
    public ResponseEntity<String> testServer(@RequestBody ServerConnectionRequestDTO request) {
        return archivingService.testConnection(request);
    }

    @GetMapping("getServer")
    public List<ArchivingServerDTO> getServerByServerCode(@RequestParam String serverCode){

        return archivingService.getServerByServerCode(serverCode);
    }
}
