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
    private String snapshotID;
    private String branchID;
    private String acronym;
    private String isActive;
    private String activeSince;
    private String createdOn;

    @ManyToOne
    private ProcessApps processApps;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "snapshots")
   private List<Tasks> tasks;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "snapshotsI")
    private List<Instances>instances;
}
