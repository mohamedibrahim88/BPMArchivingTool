package com.archiving.archivingTool.entity.archiving;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class Tasks {
    @Id
    private String ID;
    private String name;
    private String type;
    private String activityType;
    private String lane;
    private String externalID;
    @Lob
    private String jsonView;

    @ManyToOne
    private Set<Snapshots>snapshots;
}
