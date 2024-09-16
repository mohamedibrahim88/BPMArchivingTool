package com.archiving.archivingTool.model;

import java.util.List;

public class Instances {
    Overview overview ;

    List<Processes> processes;

    public Instances(Overview overview, List<Processes> processes) {
        this.overview = overview;
        this.processes = processes;
    }

    public Overview getOverview() {
        return overview;
    }

    public void setOverview(Overview overview) {
        this.overview = overview;
    }

    public List<Processes> getProcesses() {
        return processes;
    }

    public void setProcesses(List<Processes> processes) {
        this.processes = processes;
    }
}
