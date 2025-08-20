#!/bin/sh
# Ensure gradle wrapper scripts are executable in CI environments.
chmod +x "./gradlew" 2>/dev/null || true
chmod +x "./grocery_ordering_app_frontend/gradlew" 2>/dev/null || true
