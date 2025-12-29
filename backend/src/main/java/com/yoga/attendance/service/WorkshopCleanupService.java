package com.yoga.attendance.service;

import com.yoga.attendance.repository.WorkshopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class WorkshopCleanupService {
    
    @Autowired
    private WorkshopRepository workshopRepository;
    
    @Scheduled(fixedRate = 3600000) // Run every hour
    @Transactional
    public void deleteExpiredWorkshops() {
        try {
            LocalDateTime now = LocalDateTime.now();
            long countBefore = workshopRepository.count();
            
            workshopRepository.deleteByEndTimeBefore(now);
            
            long countAfter = workshopRepository.count();
            long deleted = countBefore - countAfter;
            
            if (deleted > 0) {
                System.out.println("[WorkshopCleanupService] Deleted " + deleted + " expired workshop(s) at: " + now);
            } else {
                System.out.println("[WorkshopCleanupService] No expired workshops to delete at: " + now);
            }
        } catch (Exception e) {
            System.err.println("[WorkshopCleanupService] Error cleaning up workshops: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
