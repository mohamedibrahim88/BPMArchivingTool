package com.archiving.archivingTool.service;

import com.archiving.archivingTool.DTO.Result;
import com.archiving.archivingTool.client.ConfigurationWizard;
import com.archiving.archivingTool.model.ProcessAppsList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigurationWizardService {

    @Autowired
    private ConfigurationWizard configurationWizard;

    public Result<ProcessAppsList> getGetBPMProcesses(String username, String password) {
       return configurationWizard.getProcesses(username, password);
    }
}
