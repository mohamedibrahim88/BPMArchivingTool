package com.archiving.archivingTool.client;

import com.archiving.archivingTool.dto.bpm.Result;
import com.archiving.archivingTool.entity.archiving.ArchivingServersEntity;
import com.archiving.archivingTool.model.Diagram;
import com.archiving.archivingTool.model.ExposedItemsDetails;
import com.archiving.archivingTool.model.ExposedProcesses;
import com.archiving.archivingTool.model.Step;
//import com.archiving.archivingTool.repository.bpm.TaskViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProcessTasks {

    @Autowired
    private ConfigurationWizard configurationWizard;

    @Autowired
    private RestTemplate restTemplate;
//    private final TaskViewRepository taskViewRepository;
//    public ProcessTasks(TaskViewRepository taskViewRepository) {
//        this.taskViewRepository = taskViewRepository;
//    }

    public List<Step> getProcessDiagramTasks (String processAppID, String snapshotID){
        ExposedProcesses exposedProcesses = configurationWizard.getExposedProcesses(processAppID);
        List<Step> processTasks = new ArrayList<>();
        for (ExposedItemsDetails exposedItemsDetails : exposedProcesses.getExposedItemsList()){
            if (exposedItemsDetails.getSnapshotID().equals(snapshotID)){
                System.out.println("Process: " + exposedItemsDetails);
                List<Step> processTaskDetails = getProcessTasks(exposedItemsDetails);
                for (Step s : processTaskDetails){
                    if (!s.getExternalID().isBlank()){
                        System.out.println("Diagram: " + s);
                        processTasks.add(s);
                    }else {
                        continue;
                    }
                }
            }else {
                continue;
            }
        }
        return processTasks;
    }

    public List<Step> getProcessTasks (ExposedItemsDetails exposedItemsDetails)
    {
        System.out.println( exposedItemsDetails);
        String bpmApiUrl = configurationWizard.getStringConnection("01_BAW") + "rest/bpm/wle/v1/processModel/"+exposedItemsDetails.getItemID()+"?snapshotId="+exposedItemsDetails.getSnapshotID()+"&branchId="+exposedItemsDetails.getBranchID()+"&processAppId="+exposedItemsDetails.getProcessAppID()+"&parts=diagram";
        Result<Diagram> result= new Result<Diagram>();
        List<Step>  steps = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ArchivingServersEntity archivingServersEntity = configurationWizard.getCredintials("01_BAW");
        headers.setBasicAuth(archivingServersEntity.getUserName(), archivingServersEntity.getUserPassword());
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Result<Diagram>> response = restTemplate.exchange(
                bpmApiUrl,
                HttpMethod.GET,
                requestEntity,
//              Result.class,
                new ParameterizedTypeReference<Result<Diagram>>() {}

        );
//        ResponseEntity<Result> response = restTemplate.exchange(
//                bpmApiUrl,
//                HttpMethod.GET,
//                requestEntity,
//                Result.class,
//                exposedItemsDetails
//        );
        result = response.getBody() ;
        System.out.println("response"+ response.getBody());
        System.out.println("result"+ result.getData());
        steps.addAll(result.getData().getDiagram().getStep());
        List<Step> filteredList = new ArrayList<>();
         for (Step s : steps)
         {
             System.out.println(" activity type" +s.getActivityType());
             if (s.getActivityType()!=null&&s.getActivityType().equals("task"))
             {
                 filteredList.add(s);
             }
         }
        System.out.println("URL" + bpmApiUrl);
        return filteredList;
    }

//    @Transactional("bpmTransactionManager")
//    public Optional<LswProcess> getCoachView (String processID, String versionID)
//    {
//        Optional<LswProcess> lswProcess = taskViewRepository.findById(versionID);
//
//        return lswProcess;
//    }
}
