@echo off
setlocal
cd /d "%~dp0"
if not exist validation\r7 mkdir validation\r7
call gradlew.bat clean test build runGameTestServer --console=plain
if errorlevel 1 goto failed
copy /Y build\gametest-output.log validation\r7\gametest-standalone.log >nul
if /I not "%~1"=="integrations" goto passed
call gradlew.bat runGameTestServer -PwithIntegrations -PwithKubeJS -PwithGrandpower --rerun-tasks --console=plain
if errorlevel 1 goto failed
copy /Y build\gametest-output.log validation\r7\gametest-integrations.log >nul
:passed
echo Native commands completed. Check positive GameTest completion in validation\r7.
echo This does not certify client rendering, performance, survival progression or multiplayer.
exit /b 0
:failed
echo Validation FAILED. Read build\gametest-output.log and run\logs\latest.log when available.
echo Keep your last validated JAR; do not claim this candidate has passed.
exit /b 1
