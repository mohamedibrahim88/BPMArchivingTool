package com.archiving.archivingTool.DTO;

public class ProcessSnapshotDTO {
    String name;
    String snapshotID;

    public ProcessSnapshotDTO(String name, String snapshotID) {
        this.name = name;
        this.snapshotID = snapshotID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSnapshotID() {
        return snapshotID;
    }

    public void setSnapshotID(String snapshotID) {
        this.snapshotID = snapshotID;
    }

    @Override
    public String toString() {
        return "ProcessSnapshotDTO{" +
                "name='" + name + '\'' +
                ", snapshotID='" + snapshotID + '\'' +
                '}';
    }
}
