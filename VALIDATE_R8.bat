@echo off
setlocal
cd /d "%~dp0"
echo Foundations Calculator R8 - native validation. Java 21 required.
call gradlew.bat clean test build --console=plain
if errorlevel 1 goto failed
if /I "%~1"=="integrations" (
 call gradlew.bat runGameTestServer -PwithIntegrations -PwithGrandpower -PwithKubeJS --console=plain
) else (
 call gradlew.bat runGameTestServer --console=plain
)
if errorlevel 1 goto failed
echo Native build and required server test gate completed. Run the client checklist next.
pause
exit /b 0
:failed
echo Validation failed. Keep the installed working JAR and preserve build logs.
pause
exit /b 1
