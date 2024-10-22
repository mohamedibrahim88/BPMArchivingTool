package com.archiving.archivingTool.service;

import com.archiving.archivingTool.DTO.DeleteSnapshotDTO;
import com.archiving.archivingTool.DTO.ProcessSnapshotDTO;
import com.archiving.archivingTool.DTO.InstancesListDTO;
import com.archiving.archivingTool.DTO.Result;
import com.archiving.archivingTool.client.BPMInstances;
import com.archiving.archivingTool.model.DeleteSnapshotDetails;
import com.archiving.archivingTool.model.Instances;
import com.archiving.archivingTool.model.TerminatedInstanceDetails;
import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;

@Service
@RequiredArgsConstructor
public class BPMInstancesService {

    @Autowired
    BPMInstances bpmInstances;

    public void getAllInstancesBySnapshotID(String username, String password, ProcessSnapshotDTO processSnapshotDTO) {
        bpmInstances.getAllInstancesBySnapshotID(username, password, processSnapshotDTO);
    }

    public void getInstancesBySnapshotIDAndStatus(String snapshotID, String status) {
        bpmInstances.getInstancesBySnapshotIDAndStatus(snapshotID, status);

    }

    public Instances getAllInstancesByProcessName(String name, String password, String processName, String status, String modifiedAfter, String modifiedBefore) {
        return bpmInstances.getAllInstancesByProcessName(name, password, processName, status, modifiedAfter, modifiedBefore);
    }

    public TerminatedInstanceDetails terminateInstances(InstancesListDTO instancesIDs) {
        return bpmInstances.terminateInstances(instancesIDs);
    }

    public DeleteSnapshotDetails deleteSnapshot(DeleteSnapshotDTO deleteSnapshotDTO) {
        return bpmInstances.deleteSnapshot(deleteSnapshotDTO);
    }

    public ResponseEntity<Result> getInstancesObject(InstancesListDTO instancesIDs)
    {
        return  bpmInstances.getInstancesObject(instancesIDs);
    }

}
