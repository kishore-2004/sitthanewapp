# Railway Environment Variables Setup

## CRITICAL: Check MySQL Service Variables

Railway MySQL automatically creates these variables. Make sure they are linked to your backend service:

### In Railway Dashboard:

1. Go to your **MySQL database service**
2. Click **"Variables"** tab
3. You should see these variables (Railway auto-generates them):
   - `MYSQLHOST`
   - `MYSQLPORT`
   - `MYSQLDATABASE`
   - `MYSQLUSER`
   - `MYSQLPASSWORD`
   - `MYSQL_URL`

4. Go to your **Backend service**
5. Click **"Variables"** tab
6. Click **"+ New Variable"** → **"Add Reference"**
7. Select your MySQL service
8. This will automatically link all MySQL variables

## Manual Variables to Add (Backend Service):

Add these in your backend service Variables tab:

```
JWT_SECRET=your_production_jwt_secret_key_minimum_64_characters_long_for_security
JWT_EXPIRATION=3600000
JWT_REFRESH_EXPIRATION=604800000

MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=kishorekishore2145y@gmail.com
MAIL_PASSWORD=znyhuiexdmyzbppo

FCM_ENABLED=false
```

## Verify Database Connection

After adding variables, check deployment logs for:
```
✓ Connected to MySQL database
✓ Hibernate: create table if not exists users
✓ ADMIN USER CREATED
```

## If Still Failing:

1. Check MySQL service is running (green status)
2. Verify variables are linked (should show "Reference" badge)
3. Redeploy backend service
4. Check logs for connection errors
