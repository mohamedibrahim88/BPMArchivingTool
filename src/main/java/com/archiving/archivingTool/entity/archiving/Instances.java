package com.archiving.archivingTool.entity.archiving;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class Instances {
    @Id
    private String piID;
    private String processAcronym;
    private String name;
    private String bpdName;
    private String executionStatus;
    private String lastModificationTime;
    @Lob
    private String jsonObject;

    @ManyToOne
    private Set<Snapshots> snapshotsI;

    @ManyToOne
    private Set<ProcessApps> processAppsI;
}
