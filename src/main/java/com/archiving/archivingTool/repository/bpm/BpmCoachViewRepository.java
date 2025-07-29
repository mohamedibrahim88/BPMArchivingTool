package com.archiving.archivingTool.repository.bpm;

import com.archiving.archivingTool.entity.bpm.BpmCoachView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BpmCoachViewRepository extends JpaRepository<BpmCoachView,String> {

    @Query(value = "SELECT * FROM BPM_COACH_VIEW WHERE COACH_VIEW_ID = :viewUUID ORDER BY LAST_MODIFIED DESC FETCH FIRST 1 ROWS ONLY", nativeQuery = true)
    Optional<BpmCoachView> getCoachView(@Param("viewUUID") String viewUuid);

//    @Query(value = "SELECT * FROM BPM_COACH_VIEW WHERE COACH_VIEW_ID = :viewUuid AND VERSION_ID = :versionId", nativeQuery = true)
//    Optional<String> getModifiedUser(@Param("taskID") String viewUuid, @Param("versionId") String versionId);
}
