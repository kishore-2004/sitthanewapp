-- Insert admin user with ID 2 for Railway deployment
-- Password: Admin@123 (bcrypt encoded)

INSERT INTO users (
    id, 
    name, 
    username, 
    email, 
    phone, 
    password, 
    role, 
    level, 
    months_completed, 
    created_at, 
    approved, 
    email_verified
) VALUES (
    2,
    'Railway Admin',
    'admin',
    'admin@yoga.com',
    '1234567890',
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
    'ADMIN',
    1,
    0,
    NOW(),
    true,
    true
)
ON DUPLICATE KEY UPDATE
    password = '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
    role = 'ADMIN',
    approved = true,
    email_verified = true;
