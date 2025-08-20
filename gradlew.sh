#!/usr/bin/env bash
# Compatibility wrapper for environments that call ./gradlew from repo root.
set -euo pipefail
ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
APP_WRAPPER="${ROOT_DIR}/grocery_ordering_app_frontend/gradlew"
if [ ! -x "$APP_WRAPPER" ]; then
  echo "Error: Expected wrapper at $APP_WRAPPER not found or not executable." >&2
  exit 127
fi
exec "$APP_WRAPPER" "$@"
