@echo off
cd /d "%~dp0..\frontend"

echo Step 1: Configuring EAS project...
call eas init --id

echo.
echo Step 2: Building APK...
call eas build --platform android --profile preview --non-interactive

echo.
echo Build started! Check your Expo dashboard or email for the download link.
pause
