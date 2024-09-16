package com.archiving.archivingTool.service;

import com.archiving.archivingTool.DTO.ProcessSnapshotDTO;
import com.archiving.archivingTool.DTO.Result;
import com.archiving.archivingTool.client.BPMInstances;
import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
@Service
@RequiredArgsConstructor
public class BPMInstancesService {

    @Autowired
    BPMInstances  bpmInstances;
    public void getAllInstancesBySnapshotID(String username,String password,ProcessSnapshotDTO processSnapshotDTO) {
         bpmInstances.getAllInstancesBySnapshotID(username,password,processSnapshotDTO);
    }
    public void getInstancesBySnapshotIDAndStatus (String snapshotID, String status){
        bpmInstances.getInstancesBySnapshotIDAndStatus(snapshotID,status);

    }
    public Result getAllInstancesByProcessName(String name, String password, String processName){
       return bpmInstances.getAllInstancesByProcessName(name,password,processName);
    }

}
