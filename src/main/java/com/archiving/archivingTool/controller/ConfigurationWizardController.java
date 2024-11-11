package com.archiving.archivingTool.controller;

import com.archiving.archivingTool.dto.archiving.ProcessConfigDto;
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
    public ProcessAppsData getProcesses(@RequestParam String username, @RequestParam String password){
        assert configurationWizardService != null;
       return configurationWizardService.getGetBPMProcesses(username, password);
    }

    @GetMapping("/snapshots")
    public List<InstalledSnapshots> getInstalledSnapshots(@RequestParam String username, @RequestParam String password, @RequestParam String processID){
        assert configurationWizardService != null;
        return configurationWizardService.getInstalledSnapshots(username, password, processID);
    }

    @GetMapping("/exposed/process")
    public ExposedProcesses getExposedProcesses(@RequestParam String processAppID) {
        return configurationWizardService.getExposedProcesses(processAppID);

    }
    @PostMapping("configProcess")

    public ResponseEntity<String> configProcess(ProcessConfigDto processConfigDto)
    {

        return configurationWizardService.processConfig(processConfigDto);
    }
}
