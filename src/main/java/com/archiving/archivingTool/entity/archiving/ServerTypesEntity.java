package com.archiving.archivingTool.entity.archiving;

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
@EnableAutoConfiguration
@Table(name="ServerTypes")
public class ServerTypesEntity {
    @Id
    @Column(name="id")
    private Long id;
    @Column(name="server_code")
    private String serverCode;
    @Column(name="server_type")
    private String serverType;
}
