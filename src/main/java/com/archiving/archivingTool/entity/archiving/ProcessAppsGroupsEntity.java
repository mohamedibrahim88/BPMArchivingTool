package com.archiving.archivingTool.entity.archiving;

import jakarta.persistence.*;
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
@Table(name="PROCESSAPPS_GROUPS")
public class ProcessAppsGroupsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_seq")
    @SequenceGenerator(name = "my_seq", sequenceName = "my_sequence", allocationSize = 1)
    private Long id;
    @Column(name="PROCESSAPP_ID")
    private  String appID;
    @Column(name="USER_NAME")
    private  String userName;
    @Column(name="GROUP_NAME")
    private  String groupName;
}
