package com.archiving.archivingTool.controller;

import com.archiving.archivingTool.DTO.ProcessSnapshotDTO;
import com.archiving.archivingTool.DTO.Result;
import com.archiving.archivingTool.model.Instances;
import com.archiving.archivingTool.service.BPMInstancesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public Instances getAllInstancesByProcessName(@RequestParam String name, @RequestParam String password, @RequestParam String processName){

       return bpmInstancesService.getAllInstancesByProcessName(name,password,processName);
    }


    }
