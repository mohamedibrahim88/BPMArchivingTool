package com.archiving.archivingTool.controller;

import com.archiving.archivingTool.DTO.Result;
import com.archiving.archivingTool.model.InstalledSnapshots;
import com.archiving.archivingTool.model.ProcessAppsData;
import com.archiving.archivingTool.model.ProcessAppsList;
import com.archiving.archivingTool.service.ConfigurationWizardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public List<InstalledSnapshots> getInstalledSnapshots(@RequestParam String username, @RequestParam String password){
        assert configurationWizardService != null;
        return configurationWizardService.getInstalledSnapshots(username, password);
    }
}
