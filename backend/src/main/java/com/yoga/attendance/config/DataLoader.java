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
        // Create admin user if not exists
        if (!userRepository.findByUsername("admin").isPresent()) {
            User admin = new User();
            admin.setName("Super Admin");
            admin.setUsername("admin");
            admin.setEmail("admin@yoga.com");
            admin.setPhone("1234567890");
            String rawPassword = "Admin@123";
            admin.setPassword(passwordEncoder.encode(rawPassword));
            admin.setRole(User.Role.ADMIN);
            admin.setLevel(1);
            admin.setMonthsCompleted(0);
            admin.setCreatedAt(LocalDateTime.now());
            admin.setApproved(true);
            admin.setEmailVerified(true);
            userRepository.save(admin);
            System.out.println("\n========================================");
            System.out.println("ADMIN USER CREATED");
            System.out.println("Username: admin");
            System.out.println("Password: " + rawPassword);
            System.out.println("========================================\n");
        } else {
            System.out.println("Admin user already exists");
        }

        // Create or Update superadmin user
        Optional<User> superAdminOpt = userRepository.findByUsername("superadmin");
        if (superAdminOpt.isEmpty()) {
            User superAdmin = new User();
            superAdmin.setName("Super Admin");
            superAdmin.setUsername("superadmin");
            superAdmin.setEmail("superadmin@yoga.com");
            superAdmin.setPhone("1234567891");
            String rawPassword = "Admin@123";
            superAdmin.setPassword(passwordEncoder.encode(rawPassword));
            superAdmin.setRole(User.Role.ADMIN);
            superAdmin.setLevel(1);
            superAdmin.setMonthsCompleted(0);
            superAdmin.setCreatedAt(LocalDateTime.now());
            superAdmin.setApproved(true);
            superAdmin.setEmailVerified(true);
            userRepository.save(superAdmin);
            System.out.println("\n========================================");
            System.out.println("SUPERADMIN USER CREATED");
            System.out.println("Username: superadmin");
            System.out.println("Password: " + rawPassword);
            System.out.println("========================================\n");
        } else {
            User existingSuperAdmin = superAdminOpt.get();
            String rawPassword = "Admin@123";
            existingSuperAdmin.setPassword(passwordEncoder.encode(rawPassword));
            // Ensure permissions are correct too
            existingSuperAdmin.setRole(User.Role.ADMIN);
            existingSuperAdmin.setApproved(true);
            userRepository.save(existingSuperAdmin);
            
            System.out.println("\n========================================");
            System.out.println("SUPERADMIN USER FOUND & PASSWORD UPDATED");
            System.out.println("Username: superadmin");
            System.out.println("Password: " + rawPassword);
            System.out.println("========================================\n");
        }

        // Create regular user if not exists
        if (!userRepository.findByUsername("user").isPresent()) {
            User user = new User();
            user.setName("Test User");
            user.setUsername("user");
            user.setEmail("user@yoga.com");
            user.setPhone("9876543210");
            String rawPassword = "Admin@123";
            user.setPassword(passwordEncoder.encode(rawPassword));
            user.setRole(User.Role.USER);
            user.setLevel(1);
            user.setMonthsCompleted(0);
            user.setCreatedAt(LocalDateTime.now());
            user.setApproved(true);
            user.setEmailVerified(true);
            userRepository.save(user);
            System.out.println("\n========================================");
            System.out.println("REGULAR USER CREATED");
            System.out.println("Username: user");
            System.out.println("Password: " + rawPassword);
            System.out.println("========================================\n");
        } else {
            System.out.println("Regular user already exists");
        }
    }
}
