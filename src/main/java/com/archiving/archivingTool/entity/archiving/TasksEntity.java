package com.archiving.archivingTool.entity.archiving;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "snapshots")
@Entity
@EnableAutoConfiguration
@Table(name="Tasks")
public class TasksEntity {
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
    @JsonBackReference
    private SnapshotsEntity snapshots;
}
