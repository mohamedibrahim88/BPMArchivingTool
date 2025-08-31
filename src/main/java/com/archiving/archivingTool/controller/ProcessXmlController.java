package com.archiving.archivingTool.controller;

import com.archiving.archivingTool.dto.bpm.CoachDefinitionNodeDTO;
import com.archiving.archivingTool.service.ProcessXmlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/process")
public class ProcessXmlController {

    private final ProcessXmlService xmlService;

    public ProcessXmlController(ProcessXmlService xmlService) {
        this.xmlService = xmlService;
    }

    @GetMapping("/xml")
    public ResponseEntity<List<CoachDefinitionNodeDTO>> getCoachDefinitions(@RequestParam String processAppID, @RequestParam String snapshotID, @RequestParam String instanceID) {
        try {
            List<CoachDefinitionNodeDTO> result = xmlService.getLayoutTree(processAppID,snapshotID,instanceID);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

}
