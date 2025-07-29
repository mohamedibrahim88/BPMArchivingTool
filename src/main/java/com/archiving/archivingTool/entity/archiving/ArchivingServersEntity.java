package com.archiving.archivingTool.entity.archiving;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@EnableAutoConfiguration
@Table(name="ArchivingServers")
public class ArchivingServersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "server_seq")
    @SequenceGenerator(name = "server_seq", sequenceName = "SERVER_SEQ", allocationSize = 1)
    private  Long ID;
    @Column(name="server_name")
    private  String serverName;
    @Column(name="server_Code")
    private  String serverCode;
    @Column(name="server_host_name")
    private  String serverHostName;
    @Column(name="context_path")
    private  String contextPath;
    @Column(name="repository_name")
    private  String repositoryName;
    @Column(name="database_type")
    private String databaseType;
    @Column(name="server_port")
    private String serverPort;
    @Column(name="user_name")
    private String userName;
    @Column(name="user_password")
    private String userPassword;
    @Column(name="maximum_parallel_transaction")
    private String maximumParallelTransaction;
    @Column(name="use_secure_connection")
    private int useSecureConnection;
}
