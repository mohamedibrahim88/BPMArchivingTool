package com.archiving.archivingTool.controller;

import com.archiving.archivingTool.client.BPMDocumentStore;
import com.archiving.archivingTool.dto.archiving.DocumentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/document")
@RequiredArgsConstructor

public class DocumentsController {

    private final BPMDocumentStore service;

    @GetMapping("/{instanceId}/documents")
    public ResponseEntity<List<DocumentResponseDto>> getDocuments(
            @PathVariable String instanceId) throws Exception {

        return ResponseEntity.ok(service.getDocuments(instanceId));
    }

}
