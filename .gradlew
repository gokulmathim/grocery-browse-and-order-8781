#!/bin/sh
# Hidden shim for environments that try to call ./.gradlew
SCRIPT_DIR=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
APP_DIR="$SCRIPT_DIR/grocery_ordering_app_frontend"
WRAPPER_JAR="$APP_DIR/gradle/wrapper/gradle-wrapper.jar"
JAVA_CMD="${JAVA_HOME:+$JAVA_HOME/bin/}java"
exec "$JAVA_CMD" -Dorg.gradle.appname=gradlew -classpath "$WRAPPER_JAR" org.gradle.wrapper.GradleWrapperMain "$@"
