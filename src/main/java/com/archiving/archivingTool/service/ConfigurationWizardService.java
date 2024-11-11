package com.archiving.archivingTool.service;

import com.archiving.archivingTool.client.ConfigurationWizard;
import com.archiving.archivingTool.dto.archiving.ProcessConfigDto;
import com.archiving.archivingTool.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConfigurationWizardService {

    @Autowired
    private ConfigurationWizard configurationWizard;

    public ProcessAppsData getGetBPMProcesses(String username, String password) {
       return configurationWizard.getProcesses(username, password);
    }

    public List<InstalledSnapshots> getInstalledSnapshots(String username, String password, String processID) {
        return configurationWizard.getInstalledSnapshots(username, password, processID);
    }

    public ExposedProcesses getExposedProcesses(String processAppID) {
        return configurationWizard.getExposedProcesses(processAppID);
    }

    public ResponseEntity<String> processConfig(ProcessConfigDto processConfigDto)
    {
        return configurationWizard.configProcess(processConfigDto);
    }
}
