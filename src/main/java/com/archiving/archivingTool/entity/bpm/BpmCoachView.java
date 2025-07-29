package com.archiving.archivingTool.entity.bpm;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "BPM_COACH_VIEW")
public class BpmCoachView {
    @Id
    @Column(name="COACH_VIEW_ID")
    private String coachViewId;


    @Column(name="VERSION_ID")
    private String versionID;

    @Lob
    @Column(name = "LAYOUT") // replace with your actual XML column name
    private byte[] xmlData;
    @Column(name = "LAST_MODIFIED_BY_USER_ID")
    private String lastModifiedBy;

    @Column(name = "NAME")
    private String name;

}
