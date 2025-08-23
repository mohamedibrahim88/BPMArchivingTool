package com.archiving.archivingTool.dto;

public class CreateGroupRequest {
    private String groupName;

    // Default constructor
    public CreateGroupRequest() {}

    public CreateGroupRequest(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}