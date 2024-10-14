package com.archiving.archivingTool.service;

import com.archiving.archivingTool.client.ProcessTasks;
import com.archiving.archivingTool.model.ExposedItemsDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TasksService {
    @Autowired
  private ProcessTasks processTasks;

 public void getProcessTasks(ExposedItemsDetails exposedItemsDetails)
 {
     processTasks.getProcessTasks(exposedItemsDetails);
 }
}
