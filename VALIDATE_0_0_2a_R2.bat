@echo off
setlocal EnableExtensions
cd /d "%~dp0"
set "EVIDENCE=validation\0.0.2a.R2"
if not exist "%EVIDENCE%" mkdir "%EVIDENCE%"

echo ============================================================
echo  Foundations Calculator 0.0.2a.R2 FINAL 2a VALIDATION
echo ============================================================
echo.
echo [1/4] Clean native build + JUnit
call gradlew.bat clean test build --console=plain
if errorlevel 1 goto failed
if not exist "build\libs\FoundationsCalculator-0.0.2a.R2.jar" (
 echo Expected R2 JAR is missing.
 goto failed
)

echo [2/4] Standalone GameTests - at least 124 required
call gradlew.bat runGameTestServer --console=plain
if errorlevel 1 goto failed
if not exist "build\gametest-output.log" goto failed
copy /Y "build\gametest-output.log" "%EVIDENCE%\gametest-standalone.log" >nul

if /I "%~1"=="standalone" goto releasecheck

echo [3/4] Optional integrations + KubeJS GameTests
call gradlew.bat runGameTestServer -PwithIntegrations -PwithGrandpower -PwithKubeJS --console=plain
if errorlevel 1 goto failed
if not exist "build\gametest-output.log" goto failed
copy /Y "build\gametest-output.log" "%EVIDENCE%\gametest-integrations.log" >nul

:releasecheck
echo [4/4] Release/JAR/source integrity audit
where python >nul 2>nul
if errorlevel 1 (
 echo Python was not found. Native gates passed, but release audit was not run.
 echo Run: python tools\check_release.py
 goto manual
)
python tools\check_release.py
if errorlevel 1 goto failed

:manual
echo.
echo AUTOMATED GATES PASSED.
echo Complete docs\0.0.2a.R2_ACCEPTANCE.md in a copied R9/0.0.2a world.
echo Do not call 2a final until the visual, migration, integration and performance checks pass.
pause
exit /b 0

:failed
echo.
echo VALIDATION FAILED. Keep the installed working JAR and preserve build/validation logs.
pause
exit /b 1
