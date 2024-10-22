package com.archiving.archivingTool.client;

import com.archiving.archivingTool.DTO.Result;
import com.archiving.archivingTool.model.*;
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

    public ExposedProcesses getExposedProcesses(String processAppID) {

        String bpmApiUrl = bpmServerUrl + "/rest/bpm/wle/v1/exposed/process";
        Result<ExposedProcesses> result= new Result<>();
        ExposedProcesses exposedProcesses = new ExposedProcesses();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Set authentication if needed (Basic Auth example)
        headers.setBasicAuth("wasadmin", "wasadminP@ssw0rd");
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Result<ExposedProcesses>> response = restTemplate.exchange(
                bpmApiUrl,
                HttpMethod.GET,
                requestEntity,
//                Result.class
                new ParameterizedTypeReference<Result<ExposedProcesses>>() {}
//                processName
        );
        result = response.getBody();
        System.out.println("result"+ response.getBody());
        exposedProcesses = result.getData();
        List<ExposedItemsDetails> exposedItemsDetails = exposedProcesses.getExposedItemsList();
        exposedProcesses = new ExposedProcesses();
        List<ExposedItemsDetails> exposedItemsDetailsList = new ArrayList<>();
        for (ExposedItemsDetails exposedItemsDetails1 : exposedItemsDetails){
            if (exposedItemsDetails1.getProcessAppID().equals(processAppID)){
                exposedItemsDetailsList.add(exposedItemsDetails1);
            }
        }
        exposedProcesses.setExposedItemsList(exposedItemsDetailsList);
        return exposedProcesses;
    }
}