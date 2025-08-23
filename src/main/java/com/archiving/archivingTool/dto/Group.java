package com.archiving.archivingTool.dto;

public class Group {
    private String name;
    private int memberCount;

    public Group(String name, int memberCount) {
        this.name = name;
        this.memberCount = memberCount;
    }

    public String getName() { return name; }
    public int getMemberCount() { return memberCount; }
}