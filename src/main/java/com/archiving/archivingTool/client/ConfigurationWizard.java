package com.archiving.archivingTool.client;

import com.archiving.archivingTool.DTO.Result;
import com.archiving.archivingTool.model.InstalledSnapshots;
import com.archiving.archivingTool.model.ProcessAppsData;
import com.archiving.archivingTool.model.ProcessAppsList;
import com.archiving.archivingTool.model.Processes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConfigurationWizard {
    private String bpmServerUrl = "https://bpmsrv:9443/";
    @Autowired
    private RestTemplate restTemplate;
    public ProcessAppsData getProcesses(String username, String password) {
        String bpmApiUrl = bpmServerUrl +"/rest/bpm/wle/v1/processApps";
//        List<InstalledSnapshots> installedSnapshots= new ArrayList<>();
        ProcessAppsData processAppsLists;

        Result<ProcessAppsData> result = new Result<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Set authentication if needed (Basic Auth example)
        headers.setBasicAuth(username, password);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Result<ProcessAppsData>> response = restTemplate.exchange(
                bpmApiUrl,
                HttpMethod.GET,
                requestEntity,
                //Result.class
                new ParameterizedTypeReference<Result<ProcessAppsData>>(){}
        );

//        ResponseEntity<Result> response = restTemplate.exchange(
//                bpmApiUrl,
//                HttpMethod.GET,
//                requestEntity,
//                Result.class
//               // new ParameterizedTypeReference<Result<ProcessAppsList>>(){}
//        );

        System.out.println(response.getBody());
        result = response.getBody();
        processAppsLists= result.getData();
//        processAppsLists = null;
        System.out.println("Result " +result.getData());

       // System.out.println(installedSnapshots);
        return processAppsLists;
    }

    public List<InstalledSnapshots> getInstalledSnapshots(String username, String password, String processID) {
        String bpmApiUrl = bpmServerUrl +"/rest/bpm/wle/v1/processApps";
//        List<InstalledSnapshots> installedSnapshots= new ArrayList<>();
        List<InstalledSnapshots> installedSnapshots;

        Result<ProcessAppsData> result = new Result<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Set authentication if needed (Basic Auth example)
        headers.setBasicAuth(username, password);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Result<ProcessAppsData>> response = restTemplate.exchange(
                bpmApiUrl,
                HttpMethod.GET,
                requestEntity,
                //Result.class
                new ParameterizedTypeReference<Result<ProcessAppsData>>(){}
        );

//        ResponseEntity<Result> response = restTemplate.exchange(
//                bpmApiUrl,
//                HttpMethod.GET,
//                requestEntity,
//                Result.class
//               // new ParameterizedTypeReference<Result<ProcessAppsList>>(){}
//        );

        System.out.println(response.getBody());
        result = response.getBody();
        int snapshotIndex = 0;
        for (ProcessAppsList processAppsList : result.getData().getProcessAppsList()){
            System.out.println(processAppsList.getID() + " = " + processID);
            if (processAppsList.getID().equals(processID)){
                break;
            }
            snapshotIndex ++;
        }
        installedSnapshots= result.getData().getProcessAppsList().get(snapshotIndex).getInstalledSnapshots();
//        processAppsLists = null;
        System.out.println("Result " +result.getData());

        // System.out.println(installedSnapshots);
        return installedSnapshots;
    }

}