package com.yoga.attendance.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class RootController {

    @GetMapping("/")
    public ResponseEntity<?> root() {
        return ResponseEntity.ok(Map.of(
            "service", "Yoga Attendance API",
            "status", "Running",
            "version", "1.0.0",
            "endpoints", Map.of(
                "swagger", "/swagger-ui.html",
                "api-docs", "/api-docs",
                "health", "/api/v1/auth/health"
            )
        ));
    }

    @GetMapping("/health")
    public ResponseEntity<?> health() {
        return ResponseEntity.ok(Map.of(
            "status", "UP",
            "timestamp", System.currentTimeMillis()
        ));
    }
}
