package com.archiving.archivingTool.entity.archiving;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class Groups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String groupName;

    @ManyToMany(mappedBy = "groups")
    private Set<ProcessApps>processApps;
}
