package com.archiving.archivingTool.controller;

import com.archiving.archivingTool.model.ExposedItemsDetails;
import com.archiving.archivingTool.service.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    @Autowired
    private TasksService tasksService;

    @GetMapping()
    public void getProcessTasks(@RequestBody ExposedItemsDetails exposedItemsDetails)
    {
        tasksService.getProcessTasks(exposedItemsDetails);
    }
}
