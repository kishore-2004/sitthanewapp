# 🚂 Railway Deployment Setup Guide

## ✅ **Current Status**
Your Spring Boot application is **correctly configured** to work with Railway. The issue is that **Railway environment variables are not being injected** into your backend service.

---

## 🔧 **Step-by-Step Fix**

### **Step 1: Verify MySQL Service is Running**

1. Open Railway Dashboard
2. Check that your **MySQL service** is running (green status)
3. Note the exact service name (e.g., "MySQL", "mysql", or "database")

---

### **Step 2: Link MySQL to Backend Service**

Railway needs to **explicitly link** the MySQL service to your backend. Here's how:

#### **Method A: Using Service Variables (Recommended)**

1. **Click on your backend service** (`sitthanewapp`)
2. **Go to "Variables" tab**
3. **Look for one of these options:**
   - "New Variable" → "Add Reference"
   - "Reference" button
   - "Add Service Variable"
4. **Select your MySQL service** from the dropdown
5. Railway will automatically add all MySQL variables

#### **Method B: Using Shared Variables**

1. **Click on your MySQL service**
2. **Go to "Variables" tab**
3. **Click the "Share" icon** next to each variable:
   - `MYSQLHOST`
   - `MYSQLPORT`
   - `MYSQLDATABASE`
   - `MYSQLUSER`
   - `MYSQLPASSWORD`
4. **Select your backend service** to share with

#### **Method C: Manual Copy (If above methods don't work)**

1. **Click on MySQL service**
2. **Go to "Variables" tab**
3. **Copy the values** of:
   - `MYSQLHOST`
   - `MYSQLPORT`
   - `MYSQLDATABASE`
   - `MYSQLUSER`
   - `MYSQLPASSWORD`
   - `MYSQL_URL` (if available)

4. **Click on backend service** (`sitthanewapp`)
5. **Go to "Variables" tab**
6. **Add each variable manually:**
   - Click "+ New Variable"
   - Enter name and paste value
   - Repeat for all variables

---

### **Step 3: Add Required Environment Variables**

In your backend service's Variables tab, ensure you have:

#### **Database Variables (from MySQL service):**
- ✅ `MYSQL_URL` (preferred) OR
- ✅ `MYSQLHOST`
- ✅ `MYSQLPORT`
- ✅ `MYSQLDATABASE`
- ✅ `MYSQLUSER`
- ✅ `MYSQLPASSWORD`

#### **Application Variables (add manually):**

**JWT_SECRET** (REQUIRED):
```
Name: JWT_SECRET
Value: <generate using command below>
```

**Generate JWT_SECRET in PowerShell:**
```powershell
[Convert]::ToBase64String((1..64 | ForEach-Object { Get-Random -Minimum 0 -Maximum 256 }))
```

**Or use this temporary value for testing:**
```
mySecretKey123456789012345678901234567890123456789012345678901234
```

---

### **Step 4: Verify Variable Configuration**

After adding variables, your backend service should have **at least these**:

```
✅ MYSQL_URL (or MYSQLHOST, MYSQLPORT, MYSQLDATABASE, MYSQLUSER, MYSQLPASSWORD)
✅ JWT_SECRET
✅ PORT (automatically set by Railway)
```

**Optional variables:**
- `MAIL_HOST`, `MAIL_PORT`, `MAIL_USERNAME`, `MAIL_PASSWORD` (for email)
- `REDIS_HOST`, `REDIS_PORT` (if using Redis)
- `FCM_CREDENTIALS_PATH`, `FCM_ENABLED` (for Firebase)

---

### **Step 5: Deploy**

1. **Commit and push** your updated `application.properties`:
   ```bash
   git add backend/src/main/resources/application.properties
   git commit -m "Fix Railway MySQL configuration"
   git push
   ```

2. Railway will **automatically redeploy**

---

## 🎯 **What to Look For in Logs**

### **✅ Success Indicators:**

```
HikariPool-1 - Starting...
HikariPool-1 - Added connection <MySQL-host>:3306
HikariPool-1 - Start completed.
Hibernate: create table if not exists...
Started YogaAttendanceApplication in X.XXX seconds
```

### **❌ Failure Indicators:**

```
jdbcUrl: jdbc:mysql://sitthanewapp.railway.internal:3306/railway
Connection refused
Could not create connection to database server
```

If you see `sitthanewapp.railway.internal`, it means the MySQL variables are **still not injected**.

---

## 🔍 **Troubleshooting**

### **Problem: Variables not showing up**

**Solution:**
1. Go to Railway Project Settings
2. Check "Service Connections" or "Dependencies"
3. Ensure MySQL is listed as a dependency of your backend

### **Problem: Still getting "Connection refused"**

**Check these:**
1. ✅ MySQL service is running (green status)
2. ✅ Variables are in backend service (not just MySQL service)
3. ✅ Variable names are EXACTLY: `MYSQLHOST`, `MYSQLPORT`, etc. (case-sensitive)
4. ✅ Backend service has redeployed after adding variables

### **Problem: "JWT_SECRET not found"**

**Solution:**
Add `JWT_SECRET` manually in backend service variables (see Step 3)

---

## 📋 **Quick Checklist**

Before deploying, verify:

- [ ] MySQL service is running in Railway
- [ ] MySQL variables are visible in backend service's Variables tab
- [ ] JWT_SECRET is set in backend service
- [ ] Latest code is pushed to GitHub
- [ ] Railway is set to auto-deploy from your main branch

---

## 🚀 **Alternative: Use Railway CLI**

If the UI is confusing, use Railway CLI:

```bash
# Install Railway CLI
npm install -g @railway/cli

# Login
railway login

# Link to your project
railway link

# Add variables
railway variables set JWT_SECRET=<your-secret>

# Check variables
railway variables

# Deploy
railway up
```

---

## 📞 **Still Having Issues?**

If deployment still fails:

1. **Check Railway logs** for the exact error
2. **Copy the full error message** (especially the `jdbcUrl` line)
3. **Verify variable names** match exactly (case-sensitive)
4. **Try redeploying** from Railway dashboard

---

## 🎓 **Understanding Railway's MySQL Variables**

Railway can provide MySQL connection in **two formats**:

### **Format 1: Complete URL**
```
MYSQL_URL=jdbc:mysql://mysql.railway.internal:3306/railway?user=root&password=xxxxx
```

### **Format 2: Individual Variables**
```
MYSQLHOST=mysql.railway.internal
MYSQLPORT=3306
MYSQLDATABASE=railway
MYSQLUSER=root
MYSQLPASSWORD=xxxxx
```

Your `application.properties` now supports **BOTH formats** automatically! 🎉

---

## ✨ **Next Steps After Successful Deployment**

1. Test the health endpoint: `https://your-app.railway.app/api/health`
2. Check detailed health: `https://your-app.railway.app/api/health/detailed`
3. Test login endpoint: `https://your-app.railway.app/api/v1/auth/login`
4. View Swagger UI: `https://your-app.railway.app/swagger-ui.html`

---

**Good luck! 🚀**
