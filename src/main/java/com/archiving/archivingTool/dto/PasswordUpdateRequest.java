package com.archiving.archivingTool.dto;

public class PasswordUpdateRequest {
    private String newPassword;

    // Constructor, getters, and setters
    public PasswordUpdateRequest() {}

    public PasswordUpdateRequest(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}