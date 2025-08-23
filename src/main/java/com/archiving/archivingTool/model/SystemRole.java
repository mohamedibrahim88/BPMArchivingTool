package com.archiving.archivingTool.model;

public enum SystemRole {
    SUPER_ADMIN("SUPER_ADMIN");

    private final String value;

    SystemRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    // Optional: convert string to enum safely
    public static SystemRole fromValue(String value) {
        for (SystemRole group : SystemRole.values()) {
            if (group.value.equalsIgnoreCase(value)) {
                return group;
            }
        }
        throw new IllegalArgumentException("Unknown role: " + value);
    }
}
