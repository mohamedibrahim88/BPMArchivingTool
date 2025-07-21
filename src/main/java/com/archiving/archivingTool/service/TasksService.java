package com.archiving.archivingTool.service;

import com.archiving.archivingTool.client.ProcessTasks;
import com.archiving.archivingTool.entity.bpm.Lsw_Process;
import com.archiving.archivingTool.model.ExposedItemsDetails;
import com.archiving.archivingTool.model.Step;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TasksService {
    @Autowired
  private ProcessTasks processTasks;

 public List<Step> getProcessTasks(ExposedItemsDetails exposedItemsDetails)
 {
    return processTasks.getProcessTasks(exposedItemsDetails);
 }
//    public Optional<Lsw_Process> getCoachView (String processID, String versionID){
//     return processTasks.getCoachView(processID,versionID);
//    }

}
