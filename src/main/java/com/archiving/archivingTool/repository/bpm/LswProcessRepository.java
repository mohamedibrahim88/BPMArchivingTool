package com.archiving.archivingTool.repository.bpm;

import com.archiving.archivingTool.entity.bpm.LswProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface LswProcessRepository extends JpaRepository<LswProcess, String> {

    @Query(value = "SELECT * FROM LSW_PROCESS WHERE PROCESS_ID = :taskID ORDER BY LAST_MODIFIED DESC FETCH FIRST 1 ROWS ONLY", nativeQuery = true)
    Optional<LswProcess> getCoachViewIDs(@Param("taskID") String taskID);
}
