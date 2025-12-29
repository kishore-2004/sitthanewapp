package com.yoga.attendance.scheduler;

import com.yoga.attendance.repository.WorkshopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class WorkshopCleanupScheduler {

    @Autowired
    private WorkshopRepository workshopRepository;

    // Run every hour to delete expired workshops
    @Scheduled(cron = "0 0 * * * *")
    public void deleteExpiredWorkshops() {
        try {
            LocalDateTime now = LocalDateTime.now();
            long countBefore = workshopRepository.count();
            
            workshopRepository.deleteByEndTimeBefore(now);
            
            long countAfter = workshopRepository.count();
            long deleted = countBefore - countAfter;
            
            if (deleted > 0) {
                System.out.println("Deleted " + deleted + " expired workshop(s) at: " + now);
            } else {
                System.out.println("No expired workshops to delete at: " + now);
            }
        } catch (Exception e) {
            System.err.println("Error cleaning up workshops: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
