package com.archiving.archivingTool.client;

import com.archiving.archivingTool.dto.bpm.*;
import com.archiving.archivingTool.entity.archiving.ArchivingServersEntity;
import com.archiving.archivingTool.entity.archiving.InstancesEntity;
import com.archiving.archivingTool.entity.archiving.SnapshotsEntity;
import com.archiving.archivingTool.mapper.InstancesMapper;
import com.archiving.archivingTool.model.*;
import com.archiving.archivingTool.repository.archiving.InstancesRepository;
import com.archiving.archivingTool.repository.archiving.SnapshotsRepository;
import com.archiving.archivingTool.service.LoginTokenService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class BPMInstances {
    private final InstancesRepository instancesRepository;
    private final SnapshotsRepository snapshotsRepository;

    // @Value("${bpm.server.url}")
    @Autowired
    LoginTokenService loginTokenService;
    private String bpmServerUrl = "https://bpmsrv:9443/";

    @Autowired
    private ConfigurationWizard configurationWizard;


    @Autowired
    private RestTemplate restTemplate;

    public BPMInstances(InstancesRepository instancesRepository, SnapshotsRepository snapshotsRepository) {
        this.instancesRepository = instancesRepository;
        this.snapshotsRepository = snapshotsRepository;
    }

    public Instances getAllInstancesBySnapshotID(ProcessSnapshotDTO processSnapshotDTO) {

        Optional<ArchivingServersEntity> creditials;
        creditials = configurationWizard.getCredintials("01_BAW");
        String bpmUrl = configurationWizard.getStringConnection("01_BAW");

        String bpmApiUrl = bpmUrl +"rest/bpm/wle/v1/processes/search?searchFilter=" + processSnapshotDTO.getName();
        Instances instances = new Instances();
        Result<Instances> result = new Result<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.setBasicAuth(creditials.get().getUserName(), creditials.get().getUserPassword());

        HttpEntity<Object> requestEntity = new HttpEntity<>(processSnapshotDTO, headers);
        ResponseEntity<Result<Instances>> response = restTemplate.exchange(
                bpmApiUrl,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Result<Instances>>() {},
                processSnapshotDTO
        );
        System.out.println("response"+ response.getBody());
        result = response.getBody();
        instances = result.getData();
        ArrayList<Processes> instencePerProcess = new ArrayList<>();
        for (Processes processes : instances.getProcesses()) {
            String newBpmApiUrl = bpmUrl + "rest/bpm/wle/v1/process?action=getdetails&instanceIds=" + processes.getPiid() + "&parts=data";
            if (processes.getSnapshotID().equals(processSnapshotDTO.getSnapshotID())) {
                HttpHeaders newHeaders = new HttpHeaders();
                newHeaders.setContentType(MediaType.APPLICATION_JSON);
                newHeaders.setBasicAuth(creditials.get().getUserName(), creditials.get().getUserPassword());
                HttpEntity<Object> newRequestEntity = new HttpEntity<>(newHeaders);

                ResponseEntity<Result<BulkInstanceDetails>> newResponse = restTemplate.exchange(
                        newBpmApiUrl,
                        HttpMethod.GET,
                        newRequestEntity,
                        new ParameterizedTypeReference<Result<BulkInstanceDetails>>() {
                        }
                );

                System.out.println(newResponse.getBody());
                BulkInstanceDetails testResult = new BulkInstanceDetails();
                ProcessDetails processDetails = new ProcessDetails();
                testResult = newResponse.getBody().getData();
                processDetails = testResult.getProcessDetails().get(0);

                try {
                    String data = processDetails.getData().replace("\\", "");
                    data = data.replace("\"{", "{");
                    data = data.replace("}\"", "}");
                    System.out.println(data);
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode jsonNode = mapper.readTree(data);
                    processes.setData(jsonNode);
                    instencePerProcess.add(processes);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }
        instances.setProcesses(instencePerProcess);
//        try {
//            String apiUrl = bpmUrl + "/rest/bpm/wle/v1/";
//            URL url = new URL(apiUrl);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("POST");
//
//            String auth = creditials.get().getUserName() + ":" + creditials.get().getUserPassword();
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


    public TerminatedInstanceDetails terminateInstances(InstancesListDTO instancesIDs) {

        String instancesIDsStr = instancesIDs.getInstancesIDs().toString();
//      instancesIDsStr = instancesIDsStr.replace(",", "%2C");
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

    public DeleteSnapshotDetails deleteSnapshot(DeleteSnapshotDTO deleteSnapshotDTO) {

        String processAcronym = deleteSnapshotDTO.getProcessAcronym();
        String snapshotAcronym = deleteSnapshotDTO.getSnapshotAcronym().toString();
        snapshotAcronym = snapshotAcronym.replace("[", "");
        snapshotAcronym = snapshotAcronym.replace("]", "");
        snapshotAcronym = snapshotAcronym.replace(" ", "");

        LoginTokenDTO loginTokenDTO = new LoginTokenDTO();
        loginTokenDTO.setRefreshGroups("false");
        loginTokenDTO.setRequestedLifetime("7200");
        String csrfToken = loginTokenService.createLoginToken(loginTokenDTO);
//        String csrfToken = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MjkxMTM1MzQsInN1YiI6Indhc2FkbWluIn0.kHM7n_gVtfBVXNiA5YXBisNzzQc2eKRc9lkTjiCoVKk";

        TerminatedInstanceDetails terminatedInstanceDetails = new TerminatedInstanceDetails();

        String bpmApiUrl = bpmServerUrl + "ops/std/bpm/containers/" + processAcronym + "/versions?versions=" + snapshotAcronym + "";
        DeleteSnapshotDetails deleteSnapshotDetails= new DeleteSnapshotDetails();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("wasadmin", "wasadminP@ssw0rd");
        headers.set("BPMCSRFToken", csrfToken);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<DeleteSnapshotDetails> response = restTemplate.exchange(
                bpmApiUrl,
                HttpMethod.DELETE,
                requestEntity,
//                DeleteSnapshotDetails.class
                new ParameterizedTypeReference<DeleteSnapshotDetails>() {}
        );
        deleteSnapshotDetails = response.getBody();
        return deleteSnapshotDetails;
    }

    public ResponseEntity<Result> getInstancesObject (InstancesListDTO instancesIDs)
    {
        String instancesIDsStr = instancesIDs.getInstancesIDs().toString();
//        instancesIDsStr = instancesIDsStr.replace(",", "%2C");
            instancesIDsStr = instancesIDsStr.replace("[", "");
            instancesIDsStr = instancesIDsStr.replace("]", "");
            instancesIDsStr = instancesIDsStr.replace(" ", "");
        String bpmApiUrl = bpmServerUrl + "/rest/bpm/wle/v1/process?action=getdetails&instanceIds="+instancesIDsStr+"&parts=data";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Set authentication if needed (Basic Auth example)
        headers.setBasicAuth("wasadmin", "wasadminP@ssw0rd");

        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        System.out.println("api "+ bpmApiUrl);
        ResponseEntity<Result> response = restTemplate.exchange(
                bpmApiUrl,
                HttpMethod.GET,
                requestEntity,
               Result.class
//                processName
        );
        System.out.println(response);
        return response;
    }

    @Transactional("archivingTransactionManager")
    public ResponseEntity<String> instancesConfiguration(List<Processes> instances) {
        if (instances == null) {
            return ResponseEntity.badRequest().body("instancesConfiguration is null");
        }

        System.out.println("DTO: " + instances);

        SnapshotsEntity snapshotsEntity = snapshotsRepository.findBySnapshotID(instances.get(1).getSnapshotID());
        List<InstancesEntity> instance = InstancesMapper.INSTANCE.fromInstancesToEntity(instances);
        for (InstancesEntity item : instance){
            item.setSnapshotI(snapshotsEntity);
        }
        if (instance == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to map DTO to ProcessApps entity");
        }

        System.out.println("Entity: " + instance);

        instancesRepository.saveAll(instance);
        instancesRepository.flush();

        return ResponseEntity.ok("Configured");
    }

}