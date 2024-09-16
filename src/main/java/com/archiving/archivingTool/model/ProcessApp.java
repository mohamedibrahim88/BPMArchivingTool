package com.archiving.archivingTool.model;

import java.util.ArrayList;

public class ProcessApp {
    String shortName;
    String name;
    String description;
    String richDescription;
    String lastModifiedBy;
    String defaultVersion;
    String id;
    String lastModifiedOn;
    ArrayList<InstalledSnapshots> installedSnapshots;

    public ProcessApp() {
    }

    public ProcessApp(String shortName, String name, String description, String richDescription, String lastModifiedBy, String defaultVersion, String id,
                      String lastModifiedOn, ArrayList<InstalledSnapshots> installedSnapshots) {
        this.shortName = shortName;
        this.name = name;
        this.description = description;
        this.richDescription = richDescription;
        this.lastModifiedBy = lastModifiedBy;
        this.defaultVersion = defaultVersion;
        this.id = id;
        this.lastModifiedOn = lastModifiedOn;
        this.installedSnapshots = installedSnapshots;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRichDescription() {
        return richDescription;
    }

    public void setRichDescription(String richDescription) {
        this.richDescription = richDescription;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getDefaultVersion() {
        return defaultVersion;
    }

    public void setDefaultVersion(String defaultVersion) {
        this.defaultVersion = defaultVersion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastModifiedOn() {
        return lastModifiedOn;
    }

    public void setLastModifiedOn(String lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }

    public ArrayList<InstalledSnapshots> getInstalledSnapshots() {
        return installedSnapshots;
    }

    public void setInstalledSnapshots(ArrayList<InstalledSnapshots> installedSnapshots) {
        this.installedSnapshots = installedSnapshots;
    }

}
