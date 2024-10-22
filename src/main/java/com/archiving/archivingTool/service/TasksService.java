package com.archiving.archivingTool.service;

import com.archiving.archivingTool.DTO.Result;
import com.archiving.archivingTool.client.ProcessTasks;
import com.archiving.archivingTool.model.Diagram;
import com.archiving.archivingTool.model.ExposedItemsDetails;
import com.archiving.archivingTool.model.Step;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TasksService {
    @Autowired
  private ProcessTasks processTasks;

 public List<Step> getProcessTasks(ExposedItemsDetails exposedItemsDetails)
 {
    return processTasks.getProcessTasks(exposedItemsDetails);
 }
}
