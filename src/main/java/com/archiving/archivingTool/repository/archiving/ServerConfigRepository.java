package com.archiving.archivingTool.repository.archiving;

import com.archiving.archivingTool.entity.archiving.ArchivingServersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ServerConfigRepository extends JpaRepository<ArchivingServersEntity,Long > {
    @Query("select a from ArchivingServersEntity a where a.serverCode = :serverCode")
    List<ArchivingServersEntity> findByServerCode(@Param("serverCode")String serverCode);

    @Query("select (count(s) > 0) from ArchivingServersEntity s where s.serverHostName = :serverHostName and s.serverPort = :serverPort")
    boolean isServerExistByHostnameAndPort(@Param("serverHostName") String hostName,
                                           @Param("serverPort") String port);
}
