package com.archiving.archivingTool.entity.archiving;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class Snapshots {
    @Id
    private String snapshotID;
    private String branchID;
    private String acronym;
    private String isActive;
    private String activeSince;
    private String createdOn;

    @ManyToOne
    private ProcessApps processApps;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "snapshots")
   private Set<Tasks>tasks;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "snapshotsI")
    private Set<Instances>instances;
}
