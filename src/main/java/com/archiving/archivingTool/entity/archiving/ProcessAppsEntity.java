package com.archiving.archivingTool.entity.archiving;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@EnableAutoConfiguration
@Table(name="ProcessApps")
public class ProcessAppsEntity {
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
    private List<SnapshotsEntity> snapshots;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "processAppsI")
    private List<InstancesEntity> instances;

    @ManyToMany
    @JoinTable(name = "ProcessApps_Groups",
    joinColumns = @JoinColumn(name = "processApp_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private List<GroupsEntity> groups;
}
