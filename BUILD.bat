@echo off
setlocal
cd /d "%~dp0"
echo Foundations Calculator 0.0.2a.R2 - final 2a candidate build
call gradlew.bat clean test build --console=plain %*
set "RESULT=%errorlevel%"
if not "%RESULT%"=="0" (
 echo BUILD FAILED. Keep the working installed JAR and preserve the output.
) else (
 echo Build completed. Candidate: build\libs\FoundationsCalculator-0.0.2a.R2.jar
 echo Next: run VALIDATE_0_0_2a_R2.bat before replacing the working JAR.
)
pause
exit /b %RESULT%
