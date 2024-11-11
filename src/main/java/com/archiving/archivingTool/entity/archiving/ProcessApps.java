package com.archiving.archivingTool.entity.archiving;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@EnableAutoConfiguration
public class ProcessApps {
    @Id
    private  String appID;
    private  String bpdID;
    private  String branchID;
    private  String acronym;
    private  String name;
    private  String retentionStartDate;
    private boolean isConfigured;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "processApps")
    private List<Snapshots> snapshots;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "processAppsI")
    private List<Instances> instances;

    @ManyToMany
    @JoinTable(name = "ProcessApps_Groups",
    joinColumns = @JoinColumn(name = "processApp_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private List<Groups> groups;
}
