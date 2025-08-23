package com.archiving.archivingTool.dto;

// DTO classes
public class User {
    private String username;
    private String fullName;

    public User(String username, String fullName, String email) {
        this.username = username;
        this.fullName = fullName;
    }

    public String getUsername() { return username; }
    public String getFullName() { return fullName; }
}