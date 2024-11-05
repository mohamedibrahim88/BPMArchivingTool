package com.archiving.archivingTool.entity.archiving;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
@Data
@Entity
public class ProcessApps {
    @Id
    private  String appID;
    private  String bpdID;
    private  String branchID;
    private  String acronym;
    private  String name;
    private  String relationStartDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "processApps")
    private Set<Snapshots> snapshots;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "processAppsI")
    private Set<Instances> instances;

    @ManyToMany
    @JoinTable(name = "ProcessApps_Groups",
    joinColumns = @JoinColumn(name = "processApp_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<Groups> groups;
}
