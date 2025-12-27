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
        System.out.println("🗑️  Clearing existing default users...\n");

        // Delete existing default users (admin, superadmin, testuser, user)
        String[] defaultUsernames = {"admin", "superadmin", "testuser", "user"};
        for (String username : defaultUsernames) {
            Optional<User> existingUser = userRepository.findByUsername(username);
            if (existingUser.isPresent()) {
                userRepository.delete(existingUser.get());
                System.out.println("❌ Deleted existing user: " + username);
            }
        }
        System.out.println("\n✅ Cleanup complete!\n");

        // Create Super Admin (ADMIN role)
        String superAdminPassword = "SuperAdmin@2025";
        User superAdmin = new User();
        superAdmin.setName("System Administrator");
        superAdmin.setUsername("superadmin");
        superAdmin.setEmail("admin@sitthaviruthi.com");
        superAdmin.setPhone("9876543210");
        superAdmin.setPassword(passwordEncoder.encode(superAdminPassword));
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
        System.out.println("Password: " + superAdminPassword);
        System.out.println("Role: ADMIN");
        System.out.println("Email: admin@sitthaviruthi.com");
        System.out.println("========================================\n");

        // Create Regular User (USER role)
        String testUserPassword = "TestUser@2025";
        User user = new User();
        user.setName("Test User");
        user.setUsername("testuser");
        user.setEmail("user@sitthaviruthi.com");
        user.setPhone("9123456780");
        user.setPassword(passwordEncoder.encode(testUserPassword));
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
        System.out.println("Password: " + testUserPassword);
        System.out.println("Role: USER");
        System.out.println("Email: user@sitthaviruthi.com");
        System.out.println("========================================\n");

        System.out.println("✨ User initialization complete!\n");
        System.out.println("📋 Summary:");
        System.out.println("   - Deleted old default users");
        System.out.println("   - Created fresh superadmin (ADMIN)");
        System.out.println("   - Created fresh testuser (USER)");
        System.out.println("   - All passwords set to 2025 versions\n");
    }
}
