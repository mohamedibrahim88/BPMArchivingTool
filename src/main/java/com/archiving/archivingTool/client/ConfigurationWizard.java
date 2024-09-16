package com.archiving.archivingTool.client;

import com.archiving.archivingTool.DTO.Result;
import com.archiving.archivingTool.model.ProcessAppsList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ConfigurationWizard {
    private String bpmServerUrl = "https://bpmsrv:9443/";
    @Autowired
    private RestTemplate restTemplate;
    public Result<ProcessAppsList> getProcesses(String username, String password) {
        String bpmApiUrl = bpmServerUrl +"/rest/bpm/wle/v1/processApps";
        Result result= new Result();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Set authentication if needed (Basic Auth example)
        headers.setBasicAuth(username, password);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Result> response = restTemplate.exchange(
                bpmApiUrl,
                HttpMethod.GET,
                requestEntity,
                Result.class
                //  new ParameterizedTypeReference<Result>() {},
        );
        result = response.getBody();
        System.out.println();
        return result;
    }
    }


//      try {
//     String apiUrl = bpmServerUrl + "/rest/bpm/wle/v1/processApps";


//        URL url = new URL(apiUrl);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
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
//                System.out.println("GET request: HTTP Code: " + responseCode);
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