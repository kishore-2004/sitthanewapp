package com.yoga.attendance.repository;

import com.yoga.attendance.entity.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

public interface WorkshopRepository extends JpaRepository<Workshop, Long> {
    List<Workshop> findByLevelAndTypeAndActiveTrueAndEndTimeAfterOrderByStartTimeAsc(Integer level, String type, LocalDateTime now);
    List<Workshop> findTop5ByActiveTrueOrderByCreatedAtDesc();
    
    @Transactional
    @Modifying
    void deleteByEndTimeBefore(LocalDateTime time);
    
    List<Workshop> findByLevelAndTypeAndActiveTrue(Integer level, String type);
}
