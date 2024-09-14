package com.archiving.archivingTool.client;

import com.archiving.archivingTool.DTO.ProcessSnapshotDTO;
import com.archiving.archivingTool.model.Instances;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Component
public class BPMInstances {
   // @Value("${bpm.server.url}")
    private String bpmServerUrl = "https://localhost:9443/";
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

    public void getAllInstancesByProcessName(String username, String password, String processName) {
        String bpmApiUrl = bpmServerUrl + "rest/bpm/wle/v1/processes/search?searchFilter=" + processName + "";
        ArrayList<Instances> instances = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Set authentication if needed (Basic Auth example)
        headers.setBasicAuth(username, password);
        HttpEntity<Object> requestEntity = new HttpEntity<>(processName, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                bpmApiUrl,
                HttpMethod.POST,
                requestEntity,
                String.class,
                processName
        );
        System.out.println("response"+ response.getBody());
   }


}