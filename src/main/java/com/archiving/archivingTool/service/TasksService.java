package com.archiving.archivingTool.service;

import com.archiving.archivingTool.client.ProcessTasks;
import com.archiving.archivingTool.model.ExposedItemsDetails;
import com.archiving.archivingTool.model.Step;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

     public List<Step> getProcessDiagramTasks(String processAppID, String snapshotID){
         return processTasks.getProcessDiagramTasks(processAppID,snapshotID);
     }

    //    public Optional<LswProcess> getCoachView (String processID, String versionID){
    //     return processTasks.getCoachView(processID,versionID);
    //    }

}
