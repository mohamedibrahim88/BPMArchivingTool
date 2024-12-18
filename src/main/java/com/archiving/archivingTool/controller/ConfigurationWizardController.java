package com.archiving.archivingTool.controller;

import com.archiving.archivingTool.dto.archiving.ProcessConfigDto;
import com.archiving.archivingTool.dto.archiving.SnapshotDto;
import com.archiving.archivingTool.model.ExposedProcesses;
import com.archiving.archivingTool.model.InstalledSnapshots;
import com.archiving.archivingTool.model.ProcessAppsData;
import com.archiving.archivingTool.model.ExposedItemsDetails;
import com.archiving.archivingTool.service.ConfigurationWizardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wizard")
public class ConfigurationWizardController {

    @Autowired
    private final ConfigurationWizardService configurationWizardService;

    public ConfigurationWizardController()
    {
        configurationWizardService = null;
    }

    @GetMapping()
    public ProcessAppsData getProcesses(){
        assert configurationWizardService != null;
       return configurationWizardService.getGetBPMProcesses();
    }

    @GetMapping("/snapshots")
    public List<InstalledSnapshots> getInstalledSnapshots(@RequestParam String processID){
        assert configurationWizardService != null;
        return configurationWizardService.getInstalledSnapshots(processID);
    }

    @GetMapping("/exposed/process")
    public ExposedProcesses getExposedProcesses(@RequestParam String processAppID) {
        return configurationWizardService.getExposedProcesses(processAppID);

    }
    @PostMapping("configuration/process")
    public ResponseEntity<String> configProcess(@RequestBody ProcessConfigDto processConfigDto) {

        return configurationWizardService.processConfiguration(processConfigDto);
    }

    @PostMapping("configuration/snapshots")
    public ResponseEntity<String> snapshotConfiguration(@RequestBody List<SnapshotDto> snapshotDtoList)
    {
        return configurationWizardService.snapshotConfiguration(snapshotDtoList);
    }
}
