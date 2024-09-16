package com.archiving.archivingTool.controller;

import com.archiving.archivingTool.DTO.ProcessSnapshotDTO;
import com.archiving.archivingTool.service.BPMInstancesService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void getAllInstancesByProcessName(@RequestParam String name, @RequestParam String password, @RequestParam String processName){

        bpmInstancesService.getAllInstancesByProcessName(name,password,processName);
    }


    }
