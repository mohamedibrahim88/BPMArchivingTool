package com.archiving.archivingTool.controller;

import com.archiving.archivingTool.DTO.ProcessSnapshotDTO;
import com.archiving.archivingTool.DTO.Result;
import com.archiving.archivingTool.DTO.TerminateDTO;
import com.archiving.archivingTool.model.Instances;
import com.archiving.archivingTool.model.TerminatedInstanceDetails;
import com.archiving.archivingTool.service.BPMInstancesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instances")
public class InstancesController {

    @Autowired
    private final BPMInstancesService bpmInstancesService;

    public InstancesController(BPMInstancesService bpmInstancesService) {
        this.bpmInstancesService = null;
    }
    @PostMapping()
    public void getAllInstancesBySnapshotID(@RequestParam String username,@RequestParam String password,@RequestBody ProcessSnapshotDTO processSnapshotDTO) {
    bpmInstancesService.getAllInstancesBySnapshotID(username,password,processSnapshotDTO);
    }

    @GetMapping()
    public Instances getAllInstancesByProcessName(@RequestParam String name, @RequestParam String password, @RequestParam String processName, @RequestParam (required = false) String status, @RequestParam (required = false) String modifiedAfter, @RequestParam (required = false) String modifiedBefore){

        assert bpmInstancesService != null;
        return bpmInstancesService.getAllInstancesByProcessName(name,password,processName, status, modifiedAfter, modifiedBefore);
    }

    @PostMapping("/terminate")
    public TerminatedInstanceDetails terminateInstances(@RequestBody TerminateDTO instancesIDs) {
        assert bpmInstancesService != null;
        return bpmInstancesService.terminateInstances(instancesIDs);
    }

}
