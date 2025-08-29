package com.archiving.archivingTool.dto;

public class CreateGroupRequest {
    private String name;

    // Default constructor
    public CreateGroupRequest() {}

    public CreateGroupRequest(String groupName) {
        this.name = groupName;
    }

    public String getGroupName() {
        return name;
    }

    public void setGroupName(String groupName) {
        this.name = groupName;
    }
}