package com.archiving.archivingTool.model;

public class Processes {

    String piid;
    String name;
    String bpdName;
    String snapshotID;
    String dueDate;
    String executionState;
    String lastModificationTime;

    public Processes(String piid, String name, String bpdName, String snapshotID, String dueDate, String executionState, String lastModificationTime) {
        this.piid = piid;
        this.name = name;
        this.bpdName = bpdName;
        this.snapshotID = snapshotID;
        this.dueDate = dueDate;
        this.executionState = executionState;
        this.lastModificationTime = lastModificationTime;
    }

    public String getPiid() {
        return piid;
    }

    public void setPiid(String piid) {
        this.piid = piid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBpdName() {
        return bpdName;
    }

    public void setBpdName(String bpdName) {
        this.bpdName = bpdName;
    }

    public String getSnapshotID() {
        return snapshotID;
    }

    public void setSnapshotID(String snapshotID) {
        this.snapshotID = snapshotID;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getExecutionState() {
        return executionState;
    }

    public void setExecutionState(String executionState) {
        this.executionState = executionState;
    }

    public String getLastModificationTime() {
        return lastModificationTime;
    }

    public void setLastModificationTime(String lastModificationTime) {
        this.lastModificationTime = lastModificationTime;
    }
}
