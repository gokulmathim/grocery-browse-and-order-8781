@echo off
REM Delegator script to invoke the Android module's Gradle wrapper.
setlocal enabledelayedexpansion
set SCRIPT_DIR=%~dp0
cd /d "%SCRIPT_DIR%\grocery_ordering_app_frontend"
call .\gradlew %*
