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
public class Instances {
    @Id
    private String piID;
    @Column(name="process_acronym")
    private String processAcronym;
    @Column(name="name")
    private String name;
    @Column(name="bpd_name")
    private String bpdName;
    @Column(name="execution_state")
    private String executionStatus;
    @Column(name="last_modification_time")
    private String lastModificationTime;
    @Lob
    @Column(name="json_object")
    private String jsonObject;

    @ManyToOne
    @JoinColumn(name="snapshot_id")
    private Snapshots snapshotsI;

    @ManyToOne
    @JoinColumn(name="app_id")
    private ProcessApps processAppsI;
}
