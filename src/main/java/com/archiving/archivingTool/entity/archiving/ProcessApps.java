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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private  Long ID;
    @Column(name="app_id")
    private  String appID;
    @Column(name="acronym")
    private  String acronym;
    @Column(name="name")
    private  String name;
    @Column(name="retention_start_date")
    private  String retentionStartDate;
    @Column(name="is_configured")
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
