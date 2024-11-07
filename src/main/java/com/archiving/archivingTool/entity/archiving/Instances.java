package com.archiving.archivingTool.entity.archiving;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
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
    private String processAcronym;
    private String name;
    private String bpdName;
    private String executionStatus;
    private String lastModificationTime;
    @Lob
    private String jsonObject;

    @ManyToOne
    private Snapshots snapshotsI;

    @ManyToOne
    private ProcessApps processAppsI;
}
