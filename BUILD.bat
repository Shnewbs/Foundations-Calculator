@echo off
setlocal
cd /d "%~dp0"
for /f "tokens=2 delims==" %%V in ('findstr /b "mod_version=" gradle.properties') do set "CALCULATOR_VERSION=%%V"
echo Foundations Calculator %CALCULATOR_VERSION% - alpha candidate
call gradlew.bat clean test build --console=plain %*
set "RESULT=%errorlevel%"
if not "%RESULT%"=="0" (
 echo BUILD FAILED.
) else (
 echo Build completed: build\libs\FoundationsCalculator-%CALCULATOR_VERSION%.jar
 echo Runtime validation and manual acceptance remain required.
)
pause
exit /b %RESULT%
