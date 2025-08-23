package com.archiving.archivingTool.model;

public enum SystemGroup {
    ROLE_SUPER_ADMIN("ROLE_SUPER_ADMIN");

    private final String value;

    SystemGroup(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    // Optional: convert string to enum safely
    public static SystemGroup fromValue(String value) {
        for (SystemGroup group : SystemGroup.values()) {
            if (group.value.equalsIgnoreCase(value)) {
                return group;
            }
        }
        throw new IllegalArgumentException("Unknown role: " + value);
    }
}
