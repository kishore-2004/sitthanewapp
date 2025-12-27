-- Auto-executed by Spring Boot on startup
-- Creates admin user with ID 2

INSERT INTO users (id, name, username, email, phone, password, role, level, months_completed, created_at, approved, email_verified)
SELECT 2, 'Railway Admin', 'admin', 'admin@yoga.com', '1234567890', 
       '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 
       'ADMIN', 1, 0, NOW(), true, true
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin');
