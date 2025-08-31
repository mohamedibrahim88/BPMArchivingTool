package com.archiving.archivingTool.entity.archiving;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "snapshotI")
@Entity
@EnableAutoConfiguration
@Table(name="Instances")
public class InstancesEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_seq")
    @SequenceGenerator(name = "my_seq", sequenceName = "my_sequence", allocationSize = 1)
    private long id;
    @Column(name = "PIID")
    private String piid;
    @Column(name="process_acronym")
    private String processAcronym;
    @Column(name="name")
    private String name;
    @Column(name="bpd_name")
    private String bpdName;
    @Column(name="execution_state")
    private String executionState;
    @Column(name="last_modification_time")
    private String lastModificationTime;
    @Lob
    @Column(name="json_object")
    private String jsonObject;

    @Transient
    private JsonNode data;

    @ManyToOne
    @JoinColumn(name="snapshot_id")
    @JsonBackReference
    private SnapshotsEntity snapshotI;

    @ManyToOne
    @JoinColumn(name="app_id")
    private ProcessAppsEntity processAppsID;


    public void setData(JsonNode node) {
        this.data = node;
        try {
            this.jsonObject = new com.fasterxml.jackson.databind.ObjectMapper()
                    .writeValueAsString(node);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing JsonNode", e);
        }
    }

    public JsonNode getData() {
        if (data == null && jsonObject != null) {
            try {
                data = new com.fasterxml.jackson.databind.ObjectMapper()
                        .readTree(jsonObject);
            } catch (Exception e) {
                throw new RuntimeException("Error deserializing JsonNode", e);
            }
        }
        return data;
    }
}
