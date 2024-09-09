package com.archiving.archivingTool.service;

import com.archiving.archivingTool.client.ConfigurationWizard;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigurationWizardService {

    @Autowired
    ConfigurationWizard configurationWizard;

    public void getProcesses(String bpmServerUrl, String username, String password) {
        configurationWizard.getProcesses(bpmServerUrl, username, password);
    }
}
