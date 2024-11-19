package com.archiving.archivingTool.entity.bpm;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "LSW_PROCESS")
public class Lsw_Process {
    @Column(name="PROCESS_ID")
    private String taskID;

//    @Column(name="DATA")
//    private String data;

    @Id
    @Column(name="VERSION_ID")
    private String versionID;
}
