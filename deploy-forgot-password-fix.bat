@echo off
echo ========================================
echo Deploying Forgot Password Fix to Railway
echo ========================================
echo.

echo Step 1: Adding changed files...
git add backend/src/main/java/com/yoga/attendance/controller/AuthController.java
git add backend/src/main/java/com/yoga/attendance/dto/ResetPasswordRequest.java
git add backend/src/main/java/com/yoga/attendance/service/AuthService.java
git add backend/src/main/java/com/yoga/attendance/service/EmailService.java
git add backend/src/main/resources/application.properties
git add FORGOT_PASSWORD_FIX.md
git add DEPLOY_TO_RAILWAY.md

echo.
echo Step 2: Committing changes...
git commit -m "Fix: Forgot password functionality - proper validation, error handling, and email timeouts"

echo.
echo Step 3: Pushing to Railway...
git push origin main

echo.
echo ========================================
echo Deployment initiated!
echo Check Railway dashboard for build status
echo ========================================
pause
