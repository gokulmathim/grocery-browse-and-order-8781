#!/usr/bin/env sh
# Runs Gradle from the current workspace directory, using the subproject wrapper JAR.
set -e
ROOT_DIR=$(CDPATH= cd -- "$(dirname -- "$0")/.." && pwd)
APP_DIR="$ROOT_DIR/grocery_ordering_app_frontend"
WRAPPER_JAR="$APP_DIR/gradle/wrapper/gradle-wrapper.jar"
JAVA_CMD="${JAVA_HOME:+$JAVA_HOME/bin/}java"
exec "$JAVA_CMD" -Dorg.gradle.appname=gradlew -classpath "$WRAPPER_JAR" org.gradle.wrapper.GradleWrapperMain "$@"
