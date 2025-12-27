# Railway API Testing Guide

## Base URL
```
https://sitthanewapp-production.up.railway.app
```

## Test Endpoints

### 1. Root Endpoint (Health Check)
```bash
curl https://sitthanewapp-production.up.railway.app/
```

### 2. Health Check
```bash
curl https://sitthanewapp-production.up.railway.app/health
curl https://sitthanewapp-production.up.railway.app/api/v1/auth/health
```

### 3. Swagger UI
Open in browser:
```
https://sitthanewapp-production.up.railway.app/swagger-ui.html
```

### 4. API Documentation
```
https://sitthanewapp-production.up.railway.app/api-docs
```

### 5. Login Test
```bash
curl -X POST https://sitthanewapp-production.up.railway.app/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "Admin@123"
  }'
```

### 6. Register Test
```bash
curl -X POST https://sitthanewapp-production.up.railway.app/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test User",
    "username": "testuser",
    "email": "test@example.com",
    "phone": "1234567890",
    "password": "Test@123"
  }'
```

## Admin Credentials
- **Username:** admin
- **Password:** Admin@123
- **Email:** admin@yoga.com

## What Was Configured

✅ CORS - Allow all origins for mobile app access
✅ Health Check Endpoints - For Railway monitoring
✅ Root Endpoint - Service information
✅ Security - Public endpoints configured
✅ Swagger UI - API documentation enabled

## Frontend Configuration
Your React Native app is already configured to use:
```javascript
API_URL = 'https://sitthanewapp-production.up.railway.app/api/v1'
```

## Next Steps
1. Wait 2-3 minutes for Railway to rebuild
2. Test the health endpoint
3. Try logging in with admin credentials
4. Test your mobile app
