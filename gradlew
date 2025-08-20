#!/bin/sh
# Delegator script to invoke the Android module's Gradle wrapper.
set -e
SCRIPT_DIR="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)"
cd "$SCRIPT_DIR/grocery_ordering_app_frontend"
# Pass all arguments through to the module's gradle wrapper
exec ./gradlew "$@"
