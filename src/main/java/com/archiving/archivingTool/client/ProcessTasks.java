package com.archiving.archivingTool.client;

import com.archiving.archivingTool.DTO.Result;
import com.archiving.archivingTool.model.Diagram;
import com.archiving.archivingTool.model.ExposedItemsDetails;
import com.archiving.archivingTool.model.Step;
import jdk.jshell.Diag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProcessTasks {

    private String bpmServerUrl = "https://bpmsrv:9443/";
    @Autowired
    private RestTemplate restTemplate;

    public List<Step> getProcessTasks (ExposedItemsDetails exposedItemsDetails)
    {
        System.out.println( exposedItemsDetails);
        String bpmApiUrl = bpmServerUrl + "rest/bpm/wle/v1/processModel/"+exposedItemsDetails.getItemID()+"?snapshotId="+exposedItemsDetails.getSnapshotID()+"&branchId="+exposedItemsDetails.getBranchID()+"&processAppId="+exposedItemsDetails.getProcessAppID()+"&parts=diagram";
        Result<Diagram> result= new Result<Diagram>();
        List<Step>  steps = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Set authentication if needed (Basic Auth example)
        headers.setBasicAuth("wasadmin", "wasadminP@ssw0rd");
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

}
