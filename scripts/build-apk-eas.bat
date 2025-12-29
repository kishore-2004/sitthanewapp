@echo off
echo ========================================
echo  Sittha Viruthi Yoga - APK Builder
echo ========================================
echo.

cd /d "%~dp0..\frontend"

echo Checking Expo CLI installation...
call eas --version >nul 2>&1
if errorlevel 1 (
    echo EAS CLI not found. Installing...
    call npm install -g eas-cli
)

echo.
echo Select build method:
echo 1. EAS Build (Cloud - Recommended)
echo 2. Local Build (Requires Android Studio)
echo.
set /p choice="Enter choice (1 or 2): "

if "%choice%"=="1" goto eas_build
if "%choice%"=="2" goto local_build
echo Invalid choice!
pause
exit /b 1

:eas_build
echo.
echo Starting EAS Build...
echo This will build APK in the cloud.
echo.
call eas build --platform android --profile preview
echo.
echo Build complete! Check your email or EAS dashboard for download link.
goto end

:local_build
echo.
echo Starting Local Build...
echo.
echo Step 1: Installing dependencies...
call npm install
if errorlevel 1 (
    echo Failed to install dependencies!
    pause
    exit /b 1
)

echo.
echo Step 2: Generating Android project...
call npx expo prebuild --platform android --clean
if errorlevel 1 (
    echo Failed to generate Android project!
    pause
    exit /b 1
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
cd ..
goto end

:end
echo.
pause
