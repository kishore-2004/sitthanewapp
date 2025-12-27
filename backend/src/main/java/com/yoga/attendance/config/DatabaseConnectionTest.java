package com.yoga.attendance.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.sql.DataSource;
import java.sql.Connection;

@Configuration
public class DatabaseConnectionTest {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUser;

    @Bean
    @Order(1)
    public CommandLineRunner testDatabaseConnection(DataSource dataSource) {
        return args -> {
            System.out.println("\n========================================");
            System.out.println("DATABASE CONNECTION TEST");
            System.out.println("========================================");
            System.out.println("URL: " + dbUrl);
            System.out.println("User: " + dbUser);
            
            try (Connection connection = dataSource.getConnection()) {
                System.out.println("✓ Database connection successful!");
                System.out.println("✓ Database: " + connection.getCatalog());
                System.out.println("========================================\n");
            } catch (Exception e) {
                System.err.println("✗ Database connection failed!");
                System.err.println("Error: " + e.getMessage());
                System.err.println("========================================\n");
                throw e;
            }
        };
    }
}
