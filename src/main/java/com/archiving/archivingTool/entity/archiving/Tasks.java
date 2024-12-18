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
public class Tasks {
    @Id
    private String ID;
    @Column(name="name")
    private String name;
    @Column(name="type")
    private String type;
    @Column(name="activity_type")
    private String activityType;
    @Column(name="lane")
    private String lane;
    @Column(name="external_id")
    private String externalID;
    @Lob
    @Column(name="json_view")
    private String jsonView;

    @ManyToOne
    @JoinColumn(name = "snapshot_id")
    private Snapshots snapshots;
}
