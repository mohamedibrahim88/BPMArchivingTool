//package com.archiving.archivingTool.repository.bpm;
//
//import com.archiving.archivingTool.entity.bpm.Lsw_Process;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//public interface TaskViewRepository extends JpaRepository<Lsw_Process, String> {
//
//    @Query(value = "SELECT PROCESS_ID FROM LSW_PROCESS WHERE PROCESS_ID = :taskID  AND VERSION_ID = :versionID", nativeQuery = true)
//    Lsw_Process getCoachViewIDs(@Param("taskID") String taskID,@Param("versionID") String versionID);
//}
