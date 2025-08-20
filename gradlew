#!/bin/sh
# Workspace-level Gradle wrapper shim for CI.
# Runs the subproject's Gradle wrapper JAR directly via Java, avoiding exec-bit issues.
SCRIPT_DIR=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
APP_DIR="$SCRIPT_DIR/grocery_ordering_app_frontend"
WRAPPER_JAR="$APP_DIR/gradle/wrapper/gradle-wrapper.jar"

if [ ! -f "$WRAPPER_JAR" ]; then
  echo "Gradle wrapper jar not found at $WRAPPER_JAR" >&2
  exit 127
fi

JAVA_CMD="${JAVA_HOME:+$JAVA_HOME/bin/}java"
exec "$JAVA_CMD" -Dorg.gradle.appname=gradlew -classpath "$WRAPPER_JAR" org.gradle.wrapper.GradleWrapperMain "$@"
