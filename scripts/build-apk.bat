@echo off
echo ========================================
echo  Building APK - Local Method
echo ========================================
echo.

cd /d "%~dp0..\frontend"

echo Step 1: Installing dependencies...
call npm install
if errorlevel 1 (
    echo Failed to install dependencies!
    pause
    exit /b 1
)

echo.
echo Step 2: Generating Android project (if needed)...
if not exist "android" (
    call npx expo prebuild --platform android
    if errorlevel 1 (
        echo Failed to generate Android project!
        pause
        exit /b 1
    )
)

echo.
echo Step 3: Building APK...
cd android
call gradlew assembleRelease
if errorlevel 1 (
    echo Build failed!
    cd ..
    pause
    exit /b 1
)

echo.
echo ========================================
echo  Build Successful!
echo ========================================
echo APK Location: frontend\android\app\build\outputs\apk\release\app-release.apk
echo.
cd ..
pause
