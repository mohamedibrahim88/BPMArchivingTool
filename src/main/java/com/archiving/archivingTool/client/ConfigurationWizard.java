package com.archiving.archivingTool.client;

import com.archiving.archivingTool.dto.archiving.ArchivingServerDTO;
import com.archiving.archivingTool.dto.archiving.ProcessAppGourpsUpdateDto;
import com.archiving.archivingTool.dto.archiving.ProcessConfigDto;
import com.archiving.archivingTool.dto.archiving.SnapshotDto;
import com.archiving.archivingTool.dto.bpm.Result;
import com.archiving.archivingTool.entity.archiving.*;
import com.archiving.archivingTool.mapper.ProcessGroupsMapper;
import com.archiving.archivingTool.mapper.ProcessMapper;
import com.archiving.archivingTool.mapper.SnapshotMapper;
import com.archiving.archivingTool.model.*;
import com.archiving.archivingTool.repository.archiving.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ConfigurationWizard {
    private final ProcessAppRepository processAppRepository;
    private final SnapshotsRepository snapshotsRepository;
    private final ServerTypesRepository serverTypesRepository;
    private final ServerConfigRepository serverConfigRepository;
    private final ProcessAppGroupsRepository processAppGroupsRepository;
    private PlatformTransactionManager archivingTransactionManager;
  //  private String bpmServerUrl = "https://bpmsrv:9443/";
    @Autowired
    private RestTemplate restTemplate;
    @Value("${bpm.server.url}")
    private  String bpmServerUrl;
    @Value("${bpm.username}")
    private String username;

    @Value("${bpm.password}")
    private String password;


    public ConfigurationWizard(ProcessAppRepository processAppRepository, SnapshotsRepository snapshotsRepository, ServerTypesRepository serverTypesRepository, ServerConfigRepository serverConfigRepository, ProcessAppGroupsRepository processAppGroupsRepository) {
        this.processAppRepository = processAppRepository;
        this.snapshotsRepository= snapshotsRepository;
        this.serverTypesRepository = serverTypesRepository;
        this.serverConfigRepository = serverConfigRepository;
        this.processAppGroupsRepository = processAppGroupsRepository;
    }


    @Transactional("archivingTransactionManager")
    public Optional<ServerTypesEntity> getServerType(String serverCode){

        Optional<ServerTypesEntity> serverTypesEntity =  serverTypesRepository.findByServerCode(serverCode);
        return  serverTypesEntity;
    }
    @Transactional("archivingTransactionManager")
    public String getStringConnection(String serverCode){
        String connectionURL = "";
        String protocol="";
        List<ArchivingServersEntity> archivingServersEntity =  serverConfigRepository.findByServerCode(serverCode);
        if (archivingServersEntity.get(1).getUseSecureConnection()==1){
            protocol ="https://";
        }else{
            protocol ="http://";
        }
        connectionURL=protocol+archivingServersEntity.get(1).getServerHostName()+":"
                +archivingServersEntity.get(1).getServerPort()+"/";

        System.out.println("Connection String "+ connectionURL);
        return connectionURL;
    }

    @Transactional("archivingTransactionManager")
    public ArchivingServersEntity getCredintials(String serverCode){

        ArchivingServersEntity archivingServersEntity =  serverConfigRepository.findByServerCode(serverCode).getFirst();

        return archivingServersEntity;
    }

    public ProcessAppsData getProcesses() {
        String bpmApiUrl = getStringConnection("01_BAW") +"/rest/bpm/wle/v1/processApps";
        System.out.println("Connection Done");

        ArchivingServersEntity creditials;
        creditials = getCredintials("01_BAW");
//        List<InstalledSnapshots> installedSnapshots= new ArrayList<>();
        ProcessAppsData processAppsLists;

        Result<ProcessAppsData> result = new Result<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Set authentication if needed (Basic Auth example)
        headers.setBasicAuth(creditials.getUserName(), creditials.getUserPassword());
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

    public List<InstalledSnapshots> getInstalledSnapshots(String processID) {

        String bpmApiUrl = getStringConnection("01_BAW") +"/rest/bpm/wle/v1/processApps";
        System.out.println("Connection Done");

        ArchivingServersEntity creditials;
        creditials = getCredintials("01_BAW");
//        List<InstalledSnapshots> installedSnapshots= new ArrayList<>();
        List<InstalledSnapshots> installedSnapshots;

        Result<ProcessAppsSnpashots> result = new Result<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Set authentication if needed (Basic Auth example)
        headers.setBasicAuth(creditials.getUserName(), creditials.getUserPassword());
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Result<ProcessAppsSnpashots>> response = restTemplate.exchange(
                bpmApiUrl,
                HttpMethod.GET,
                requestEntity,
                //Result.class
                new ParameterizedTypeReference<Result<ProcessAppsSnpashots>>(){}
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
        for (ProcessAppsSnapshotsList processAppsList : result.getData().getProcessAppsList()){
            System.out.println(processAppsList.getAppID() + " = " + processID);
            if (processAppsList.getAppID().equals(processID)){
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

    @Transactional("archivingTransactionManager")
    public ResponseEntity<String> processConfiguration(ProcessConfigDto processConfigDto) {
        if (processConfigDto == null) {
            return ResponseEntity.badRequest().body("ProcessConfigDto is null");
        }

        System.out.println("DTO: " + processConfigDto);

        ProcessAppsEntity processApps = ProcessMapper.INSTANCE.fromProcessAppDtoToEntity(processConfigDto);

        if (processApps == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to map DTO to ProcessApps entity");
        }

        System.out.println("Entity: " + processApps);

        processAppRepository.save(processApps);
        processAppRepository.flush();

        List<ProcessAppsGroupsEntity> processAppsGroups = ProcessGroupsMapper.INSTANCE.fromDtoToListEntity(processConfigDto);

        if (processAppsGroups == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to map DTO to ProcessApps entity");
        }

        System.out.println("Entity: " + processAppsGroups);

        processAppGroupsRepository.saveAll(processAppsGroups);
        processAppGroupsRepository.flush();

        return ResponseEntity.ok("Configured");
    }

    @Transactional("archivingTransactionManager")
    public ResponseEntity<String> deleteProcessAppGroups(String appID) {
        processAppGroupsRepository.deleteByAppID(appID);
        return ResponseEntity.ok("Deleted");
    }


    @Transactional("archivingTransactionManager")
    public ResponseEntity<String> updateProcessAppGroups(ProcessAppGourpsUpdateDto processAppGourpsUpdateDto) {

        processAppGroupsRepository.deleteByAppID(processAppGourpsUpdateDto.getAppID());

        List<ProcessAppsGroupsEntity> processAppsGroups = ProcessGroupsMapper.INSTANCE.fromProcessAppGroupsUpdateDtoToListEntity(processAppGourpsUpdateDto);

        if (processAppsGroups == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to map DTO to ProcessApps entity");
        }

        System.out.println("Entity: " + processAppsGroups);

        processAppGroupsRepository.saveAll(processAppsGroups);
        processAppGroupsRepository.flush();
        return ResponseEntity.ok("Updated");

    }

    @Transactional("archivingTransactionManager")
    public ResponseEntity<ProcessConfigDto> getProcessAppConfig(String appID) {


        ProcessConfigDto processConfigDto = new ProcessConfigDto();

        List<ProcessAppsEntity> processAppsEntity =  processAppRepository.getByAppID(appID);

        if (processAppsEntity.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(processConfigDto);
        }

        List<ProcessAppsGroupsEntity> processAppsGroupsEntityList = processAppGroupsRepository.getByAppID(appID);

        processConfigDto = ProcessMapper.INSTANCE.fromProcessAppEntityToDto(processAppsEntity.getFirst());

        if (processConfigDto == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(processConfigDto);
        }

        System.out.println(processConfigDto);
        List<String> groupNames = new ArrayList<>();
        List<String> userNames = new ArrayList<>();
        for (ProcessAppsGroupsEntity processAppsGroupsEntity : processAppsGroupsEntityList){
            if (processAppsGroupsEntity.getGroupName() != null) {
                groupNames.add(processAppsGroupsEntity.getGroupName());
            }
            if (processAppsGroupsEntity.getUserName() != null) {
                userNames.add(processAppsGroupsEntity.getUserName());
            }
        }

        processConfigDto.setAssignedUsers(userNames);
        processConfigDto.setAssignedGroups(groupNames);

        System.out.println("Entity: " + processConfigDto);


        return ResponseEntity.ok(processConfigDto);
    }


    @Transactional("archivingTransactionManager")
    public ResponseEntity<String> snapshotConfiguration (List<SnapshotDto> snapshotDtoList)
    {
        System.out.println(snapshotDtoList);

        if (snapshotDtoList == null) {
            return ResponseEntity.badRequest().body("ProcessConfigDto is null");
        }

        List<SnapshotsEntity> snapshots = SnapshotMapper.INSTANCE.fromSnapshotDtoListToEntity(snapshotDtoList);

        System.out.println(snapshots);
        if (snapshots == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to map DTO to ProcessApps entity");
        }


        snapshotsRepository.saveAll(snapshots);
        snapshotsRepository.flush();

        return ResponseEntity.ok("Configured");
    }


}