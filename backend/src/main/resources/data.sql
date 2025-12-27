-- Insert Super Admin User
-- Username: admin
-- Password: Admin@123

INSERT INTO users (name, username, email, phone, password, role, level, months_completed, created_at, approved, email_verified)
VALUES 
('Super Admin', 'admin', 'admin@yoga.com', '1234567890', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN', 1, 0, NOW(), true, true),
('Test User', 'user', 'user@yoga.com', '9876543210', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER', 1, 0, NOW(), true, true)
ON DUPLICATE KEY UPDATE username = username;
