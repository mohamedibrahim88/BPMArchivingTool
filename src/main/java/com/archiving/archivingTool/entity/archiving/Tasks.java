package com.archiving.archivingTool.entity.archiving;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
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
    private Snapshots snapshots;
}
