package com.archiving.archivingTool.controller;

import com.archiving.archivingTool.model.ExposedItemsDetails;
import com.archiving.archivingTool.model.Step;
import com.archiving.archivingTool.service.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
