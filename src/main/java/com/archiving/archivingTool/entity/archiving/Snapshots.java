package com.archiving.archivingTool.entity.archiving;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@EnableAutoConfiguration
public class Snapshots {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private  Long ID;
    @Column(name="snapshot_id")
    private String snapshotID;
    @Column(name="branch_id")
    private String branchID;
    @Column(name="acronym")
    private String acronym;
    @Column(name="is_active")
    private String isActive;
    @Column(name="active_since")
    private String activeSince;
    @Column(name="created_on")
    private String createdOn;

    @ManyToOne
    @JoinColumn(name="app_id")
    private ProcessApps processApps;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "snapshots")
   private List<Tasks> tasks;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "snapshotsI")
    private List<Instances>instances;
}
