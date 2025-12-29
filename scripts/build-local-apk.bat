@echo off
echo Building APK locally...
cd /d "%~dp0..\frontend"

echo Installing dependencies...
call npm install

echo Generating Android project...
call npx expo prebuild --platform android --clean

echo Building APK...
cd android
call gradlew assembleRelease

echo.
echo APK built at: frontend\android\app\build\outputs\apk\release\app-release.apk
pause
