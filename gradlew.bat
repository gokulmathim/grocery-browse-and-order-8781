@echo off
setlocal
set SCRIPT_DIR=%~dp0
set APP_DIR=%SCRIPT_DIR%grocery_ordering_app_frontend
set WRAPPER_JAR=%APP_DIR%\gradle\wrapper\gradle-wrapper.jar
if not exist "%WRAPPER_JAR%" (
  echo Gradle wrapper jar not found at %WRAPPER_JAR% 1>&2
  exit /b 127
)
set JAVA_EXE=java.exe
if defined JAVA_HOME set JAVA_EXE=%JAVA_HOME%\bin\java.exe
"%JAVA_EXE%" -Dorg.gradle.appname=gradlew -classpath "%WRAPPER_JAR%" org.gradle.wrapper.GradleWrapperMain %*
endlocal
