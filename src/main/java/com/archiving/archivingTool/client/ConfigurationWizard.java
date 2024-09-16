package com.archiving.archivingTool.client;

import com.archiving.archivingTool.model.InstalledSnapshots;
import com.archiving.archivingTool.model.ProcessApp;
import com.fasterxml.jackson.core.io.JsonStringEncoder;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONArray;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import org.json.JSONObject;

@Component
public class ConfigurationWizard {

    public ArrayList<ProcessApp> getProcesses(String bpmServerUrl, String username, String password) {

        ArrayList<ProcessApp> processApp = new ArrayList<>();
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

                JSONObject jsonObject = new JSONObject(responseCode);
                JSONArray processesArray = jsonObject.getJSONArray("processAppsList");


                for (int i = 0; i <= processesArray.length(); i++){
                    JSONObject processAppObject = processesArray.getJSONObject(i);
                    ProcessApp processApp1 = new ProcessApp();

                    processApp1.setShortName(processAppObject.getString("shortName"));
                    processApp1.setName(processAppObject.getString("name"));
                    processApp1.setDescription(processAppObject.getString("description"));
                    processApp1.setRichDescription(processAppObject.getString("richDescription"));
                    processApp1.setLastModifiedBy(processAppObject.getString("lastModifiedBy"));
                    processApp1.setDefaultVersion(processAppObject.getString("defaultVersion"));
                    processApp1.setId(processAppObject.getString("ID"));
                    processApp1.setLastModifiedOn(processAppObject.getString("lastModified_on"));

                    JSONArray snapshotsArray = processAppObject.getJSONArray("installedSnapshots");
                    ArrayList<InstalledSnapshots> snapshots = new ArrayList<>();

                    for (int j = 0; j <= snapshotsArray.length(); j++){
                        JSONObject snapshotObject = snapshotsArray.getJSONObject(i);
                        InstalledSnapshots snapshot = new InstalledSnapshots();

                        snapshot.setName(snapshotObject.getString("name"));
                        snapshot.setAcronym(snapshotObject.getString("acronym"));
                        snapshot.setActive(snapshotObject.getString("active"));
                        snapshot.setCreatedOn(snapshotObject.getString("createdOn"));
                        snapshot.setSnapshotTip(snapshotObject.getBoolean("snapshotTip"));
                        snapshot.setBranchID(snapshotObject.getString("branchID"));
                        snapshot.setBranchName(snapshotObject.getString("branchName"));
                        snapshot.setID(snapshotObject.getString("ID"));

                        snapshots.add(snapshot);
                    }

                    processApp1.setInstalledSnapshots(snapshots);
                    processApp.add(processApp1);
                }
                return processApp;

//                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                StringBuffer response = new StringBuffer();
//
//                while (in.readLine() != null) {
//                    response.append(in.readLine());
//                }
//                in.close();
//                System.out.println("Response: " + response.toString());
            } else {
                System.out.println("GET request failed: HTTP Code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return processApp;
    }
}
