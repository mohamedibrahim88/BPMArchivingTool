package com.archiving.archivingTool.controller;

import com.archiving.archivingTool.model.ExposedItemsDetails;
import com.archiving.archivingTool.model.Step;
import com.archiving.archivingTool.service.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    @Autowired
    private TasksService tasksService;

    @GetMapping()
    public List<Step> getProcessTasks(@RequestBody ExposedItemsDetails exposedItemsDetails)
    {
      return tasksService.getProcessTasks(exposedItemsDetails);
    }

    @GetMapping("/diagram")
    public List<Step> getProcessDiagramTasks(@RequestParam String processAppID, @RequestParam String snapshotID)
    {
        return tasksService.getProcessDiagramTasks(processAppID,snapshotID);
    }

//    @GetMapping("/view")
//    public Optional<LswProcess> getCoachView(@RequestParam String processID, @RequestParam String versionID)
//    {
//        return tasksService.getCoachView(processID,versionID);
//
//    }
}
