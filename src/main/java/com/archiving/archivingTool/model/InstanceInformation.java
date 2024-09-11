package com.archiving.archivingTool.model;

public class InstanceInformation {

    String instanceID;
    String name;
    String bpdName;
    String dueDate;
    String executionState;
    String creationDate;
    String lastModificationTime;

    public InstanceInformation(String instanceID, String name, String bpdName, String dueDate,
                               String executionState, String creationDate, String lastModificationTime) {
        this.instanceID = instanceID;
        this.name = name;
        this.bpdName = bpdName;
        this.dueDate = dueDate;
        this.executionState = executionState;
        this.creationDate = creationDate;
        this.lastModificationTime = lastModificationTime;
    }

    public String getInstanceID() {
        return instanceID;
    }

    public void setInstanceID(String instanceID) {
        this.instanceID = instanceID;
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

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getLastModificationTime() {
        return lastModificationTime;
    }

    public void setLastModificationTime(String lastModificationTime) {
        this.lastModificationTime = lastModificationTime;
    }

    @Override
    public String toString() {
        return "InstanceInformation{" +
                "instanceID='" + instanceID + '\'' +
                ", name='" + name + '\'' +
                ", bpdName='" + bpdName + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", executionState='" + executionState + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", lastModificationTime='" + lastModificationTime + '\'' +
                '}';
    }
}
