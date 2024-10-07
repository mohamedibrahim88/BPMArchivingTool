package com.archiving.archivingTool.service;

import com.archiving.archivingTool.DTO.Result;
import com.archiving.archivingTool.client.ConfigurationWizard;
import com.archiving.archivingTool.model.InstalledSnapshots;
import com.archiving.archivingTool.model.ProcessAppsData;
import com.archiving.archivingTool.model.ProcessAppsList;
import com.archiving.archivingTool.model.Processes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
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
}
