package com.archiving.archivingTool.client;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

@Component
public class ConfigurationWizard {

    public void getProcesses(String bpmServerUrl, String username, String password) {
        try {
            String apiUrl = bpmServerUrl + "/rest/bpm/wle/v1/processApps";
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            String auth = username + ":" + password;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes("UTF-8"));
            String authHeaderValue = "Basic " + encodedAuth;
            conn.setRequestProperty("Authorization", authHeaderValue);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {

                System.out.println("GET request: HTTP Code: " + responseCode);

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer response = new StringBuffer();

                while (in.readLine() != null) {
                    response.append(in.readLine());
                }
                in.close();
                System.out.println("Response: " + response.toString());
            } else {
                System.out.println("GET request failed: HTTP Code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
