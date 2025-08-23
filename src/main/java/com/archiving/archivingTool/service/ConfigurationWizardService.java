package com.archiving.archivingTool.service;

import com.archiving.archivingTool.client.ConfigurationWizard;
import com.archiving.archivingTool.dto.archiving.ProcessAppGourpsUpdateDto;
import com.archiving.archivingTool.dto.archiving.ProcessConfigDto;
import com.archiving.archivingTool.dto.archiving.SnapshotDto;
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

    public ProcessAppsData getGetBPMProcesses() {
       return configurationWizard.getProcesses();
    }

    public List<InstalledSnapshots> getInstalledSnapshots(String processID) {
        return configurationWizard.getInstalledSnapshots(processID);
    }

    public ExposedProcesses getExposedProcesses(String processAppID) {
        return configurationWizard.getExposedProcesses(processAppID);
    }

    public ResponseEntity<String> processConfiguration(ProcessConfigDto processConfigDto) {
        return configurationWizard.processConfiguration(processConfigDto);
    }

    public ResponseEntity<String> deleteProcessAppGroups(String appID) {
        return configurationWizard.deleteProcessAppGroups(appID);
    }

    public ResponseEntity<String> updateProcessAppGroups(ProcessAppGourpsUpdateDto processAppGourpsUpdateDto) {
        return configurationWizard.updateProcessAppGroups(processAppGourpsUpdateDto);
    }

    public ResponseEntity<String> snapshotConfiguration(List<SnapshotDto> snapshotDtoList)
    {
        return configurationWizard.snapshotConfiguration(snapshotDtoList);
    }
}
