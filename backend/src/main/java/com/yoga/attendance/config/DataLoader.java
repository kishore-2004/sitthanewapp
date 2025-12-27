package com.yoga.attendance.config;

import com.yoga.attendance.entity.User;
import com.yoga.attendance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n🔄 Initializing default users for Railway deployment...\n");

        // Create Super Admin (ADMIN role)
        if (!userRepository.findByUsername("superadmin").isPresent()) {
            User superAdmin = new User();
            superAdmin.setName("System Administrator");
            superAdmin.setUsername("superadmin");
            superAdmin.setEmail("admin@sitthaviruthi.com");
            superAdmin.setPhone("9876543210");
            String rawPassword = "SuperAdmin@2025";
            superAdmin.setPassword(passwordEncoder.encode(rawPassword));
            superAdmin.setRole(User.Role.ADMIN);
            superAdmin.setLevel(1);
            superAdmin.setMonthsCompleted(0);
            superAdmin.setCreatedAt(LocalDateTime.now());
            superAdmin.setApproved(true);
            superAdmin.setEmailVerified(true);
            userRepository.save(superAdmin);
            System.out.println("========================================");
            System.out.println("✅ SUPER ADMIN CREATED");
            System.out.println("Username: superadmin");
            System.out.println("Password: " + rawPassword);
            System.out.println("Role: ADMIN");
            System.out.println("Email: admin@sitthaviruthi.com");
            System.out.println("========================================\n");
        } else {
            System.out.println("ℹ️  Super admin already exists\n");
        }

        // Create Regular User (USER role)
        if (!userRepository.findByUsername("testuser").isPresent()) {
            User user = new User();
            user.setName("Test User");
            user.setUsername("testuser");
            user.setEmail("user@sitthaviruthi.com");
            user.setPhone("9123456780");
            String rawPassword = "TestUser@2025";
            user.setPassword(passwordEncoder.encode(rawPassword));
            user.setRole(User.Role.USER);
            user.setLevel(1);
            user.setMonthsCompleted(0);
            user.setCreatedAt(LocalDateTime.now());
            user.setApproved(true);
            user.setEmailVerified(true);
            userRepository.save(user);
            System.out.println("========================================");
            System.out.println("✅ TEST USER CREATED");
            System.out.println("Username: testuser");
            System.out.println("Password: " + rawPassword);
            System.out.println("Role: USER");
            System.out.println("Email: user@sitthaviruthi.com");
            System.out.println("========================================\n");
        } else {
            System.out.println("ℹ️  Test user already exists\n");
        }

        System.out.println("✨ User initialization complete!\n");
    }
}
