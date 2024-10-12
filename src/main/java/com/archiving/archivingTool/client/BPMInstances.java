package com.archiving.archivingTool.client;

import com.archiving.archivingTool.DTO.ProcessSnapshotDTO;
import com.archiving.archivingTool.DTO.Result;
import com.archiving.archivingTool.DTO.TerminateDTO;
import com.archiving.archivingTool.model.Instances;
import com.archiving.archivingTool.model.TerminatedInstanceDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class BPMInstances {
   // @Value("${bpm.server.url}")
    private String bpmServerUrl = "https://bpmsrv:9443/";
    @Autowired
    private RestTemplate restTemplate;
    public ArrayList<Instances> getAllInstancesBySnapshotID(String username, String password, ProcessSnapshotDTO processSnapshotDTO) {
        String bpmApiUrl = bpmServerUrl +"rest/bpm/wle/v1/processes/search?offset=0&limit=20";
        ArrayList<Instances> instances = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Set authentication if needed (Basic Auth example)
        headers.setBasicAuth(username, password);
        HttpEntity<Object> requestEntity = new HttpEntity<>(processSnapshotDTO, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                bpmApiUrl,
                HttpMethod.POST,
                requestEntity,
                String.class,
                processSnapshotDTO
        );
         System.out.println("response"+ response.getBody());
//        try {
//            String apiUrl = bpmServerUrl + "/rest/bpm/wle/v1/";
//            URL url = new URL(apiUrl);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("POST");
//
//            String auth = username + ":" + password;
//            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes("UTF-8"));
//            String authHeaderValue = "Basic " + encodedAuth;
//            conn.setRequestProperty("Authorization", authHeaderValue);
//            conn.setRequestProperty("Content-Type", "application/json");
//            conn.setRequestProperty("Accept", "application/json");
//
//            int responseCode = conn.getResponseCode();
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//
//                System.out.println("POST request: HTTP Code: " + responseCode);
//
//                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                StringBuffer response = new StringBuffer();
//
//                while (in.readLine() != null) {
//                    response.append(in.readLine());
//                }
//                in.close();
//                System.out.println("Response: " + response.toString());
//            } else {
//                System.out.println("GET request failed: HTTP Code: " + responseCode);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return instances;
    }

    public void getInstancesBySnapshotIDAndStatus(String snapshotID, String Status) {
    }


    public Instances getAllInstancesByProcessName(String username, String password, String processName, String status, String modifiedAfter, String modifiedBefore) {
        String filter = "";
        if (status != null){
           filter = filter + "&statusFilter=" + status;
        }
        if (modifiedAfter != null){
            filter = filter + "&modifiedAfter=" + modifiedAfter;
        }
        if (modifiedBefore != null){
            filter = filter + "&modifiedBefore=" + modifiedBefore;
        }
        String bpmApiUrl = bpmServerUrl + "rest/bpm/wle/v1/processes/search?searchFilter=" + processName + filter +"";
        Result<Instances> result= new Result<>();
        Instances instances = new Instances();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Set authentication if needed (Basic Auth example)
        headers.setBasicAuth(username, password);
        HttpEntity<Object> requestEntity = new HttpEntity<>(processName, headers);

        ResponseEntity<Result<Instances>> response = restTemplate.exchange(
                bpmApiUrl,
                HttpMethod.GET,
                requestEntity,
//                Result.class,
                new ParameterizedTypeReference<Result<Instances>>() {},
                processName
        );
        result = response.getBody();
        instances = result.getData();
        System.out.println();
        return instances;
    }


    public TerminatedInstanceDetails terminateInstances(TerminateDTO instancesIDs) {

        String instancesIDsStr = instancesIDs.getInstancesIDs().toString();
//        instancesIDsStr = instancesIDsStr.replace(",", "%2C");
        instancesIDsStr = instancesIDsStr.replace("[", "");
        instancesIDsStr = instancesIDsStr.replace("]", "");
        instancesIDsStr = instancesIDsStr.replace(" ", "");

        TerminatedInstanceDetails terminatedInstanceDetails = new TerminatedInstanceDetails();

        String bpmApiUrl = bpmServerUrl + "rest/bpm/wle/v1/process/bulk?instanceIds=" + instancesIDsStr + "&action=terminate&parts=header";
        Result<TerminatedInstanceDetails> result= new Result<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Set authentication if needed (Basic Auth example)
        headers.setBasicAuth("wasadmin", "wasadminP@ssw0rd");

        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Result<TerminatedInstanceDetails>> response = restTemplate.exchange(
                bpmApiUrl,
                HttpMethod.POST,
                requestEntity,
//                Result.class
                new ParameterizedTypeReference<Result<TerminatedInstanceDetails>>() {}
//                processName
        );
        result = response.getBody();
        terminatedInstanceDetails = result.getData();
        System.out.println();
        return terminatedInstanceDetails;
    }

}