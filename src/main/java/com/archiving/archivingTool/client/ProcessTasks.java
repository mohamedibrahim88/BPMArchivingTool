package com.archiving.archivingTool.client;

import com.archiving.archivingTool.DTO.Result;
import com.archiving.archivingTool.model.Diagram;
import com.archiving.archivingTool.model.ExposedItemsDetails;
import com.archiving.archivingTool.model.Instances;
import com.archiving.archivingTool.model.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class ProcessTasks {

    private String bpmServerUrl = "https://bpmsrv:9443/";
    @Autowired
    private RestTemplate restTemplate;

    public void getProcessTasks (ExposedItemsDetails exposedItemsDetails)
    {
        String bpmApiUrl = bpmServerUrl + "rest/bpm/wle/v1/processModel/"+exposedItemsDetails.getItemID()+"?snapshotId="+exposedItemsDetails.getSnapshotID()+"&branchId="+exposedItemsDetails.getBranchID()+"&processAppId="+exposedItemsDetails.getProcessAppID()+"&parts=diagram";
        Result<Diagram> result= new Result<>();
        List<Step>  steps;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Set authentication if needed (Basic Auth example)
        headers.setBasicAuth("wasadmin", "wasadminP@ssw0rd");
        HttpEntity<Object> requestEntity = new HttpEntity<>(exposedItemsDetails, headers);

        ResponseEntity<Result<Diagram>> response = restTemplate.exchange(
                bpmApiUrl,
                HttpMethod.GET,
                requestEntity,
//                Result.class,
                new ParameterizedTypeReference<Result<Diagram>>() {},
                exposedItemsDetails
        );
        result = response.getBody();
        System.out.println(result);
    }

}
