package com.archiving.archivingTool.entity.bpm;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "LSW_PROCESS")
public class LswProcess {
    @Column(name="PROCESS_ID")
    private String taskID;

    @Id
    @Column(name="VERSION_ID")
    private String versionID;

    @Lob
    @Column(name = "DATA") // replace with your actual XML column name
    private byte[] xmlData;
}
