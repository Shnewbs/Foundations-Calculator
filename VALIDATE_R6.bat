@echo off
setlocal
cd /d "%~dp0"
if not exist validation\r6 mkdir validation\r6
call gradlew.bat clean test build runGameTestServer --console=plain
if errorlevel 1 goto failed
copy /Y build\gametest-output.log validation\r6\gametest-standalone.log >nul
if /I not "%~1"=="integrations" goto passed
call gradlew.bat runGameTestServer -PwithIntegrations -PwithKubeJS -PwithGrandpower --rerun-tasks --console=plain
if errorlevel 1 goto failed
copy /Y build\gametest-output.log validation\r6\gametest-integrations.log >nul
:passed
echo Native commands completed. Check positive GameTest completion in validation\r6.
echo This does not certify client rendering, performance, survival progression or multiplayer.
exit /b 0
:failed
echo Validation FAILED. Read build\gametest-output.log and run\logs\latest.log when available.
echo Keep the working R5 JAR; do not claim this candidate has passed.
exit /b 1
