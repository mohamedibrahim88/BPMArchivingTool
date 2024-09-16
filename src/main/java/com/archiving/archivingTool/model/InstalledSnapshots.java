package com.archiving.archivingTool.model;

public class InstalledSnapshots {

    String name;
    String acronym;
    String active;
    //String activeSince;
    String createdOn;
    boolean snapshotTip;
    String branchID;
    String branchName;
    String ID;

    public InstalledSnapshots() {
    }

    public InstalledSnapshots(String name, String acronym, String active, String createdOn, boolean snapshotTip, String branchID, String branchName, String ID) {
        this.name = name;
        this.acronym = acronym;
        this.active = active;
        this.createdOn = createdOn;
        this.snapshotTip = snapshotTip;
        this.branchID = branchID;
        this.branchName = branchName;
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public boolean isSnapshotTip() {
        return snapshotTip;
    }

    public void setSnapshotTip(boolean snapshotTip) {
        this.snapshotTip = snapshotTip;
    }

    public String getBranchID() {
        return branchID;
    }

    public void setBranchID(String branchID) {
        this.branchID = branchID;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
