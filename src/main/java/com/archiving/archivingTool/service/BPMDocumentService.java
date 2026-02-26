package com.archiving.archivingTool.service;


import com.archiving.archivingTool.client.BPMDocumentStore;
import com.archiving.archivingTool.dto.archiving.DocumentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BPMDocumentService {

    @Autowired
    BPMDocumentStore bpmDocumentStore;

    public List<DocumentResponseDto> getFileServerDocument (String instanceId) throws Exception{

        return bpmDocumentStore.getDocuments(instanceId);

    }
}
