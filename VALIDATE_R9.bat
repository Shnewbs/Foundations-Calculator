@echo off
setlocal
cd /d "%~dp0"
echo Foundations Calculator R9 - final 0.0.1a validation. Java 21 required.
call gradlew.bat clean test build --console=plain
if errorlevel 1 goto failed
if /I "%~1"=="integrations" (
 call gradlew.bat runGameTestServer -PwithIntegrations -PwithGrandpower -PwithKubeJS --console=plain
) else (
 call gradlew.bat runGameTestServer --console=plain
)
if errorlevel 1 goto failed
echo Native build and required server test gate completed.
echo Complete docs\R9_ACCEPTANCE.md in a copied world before freezing 0.0.1a.
pause
exit /b 0
:failed
echo Validation failed. Keep the installed working JAR and preserve build logs.
pause
exit /b 1
