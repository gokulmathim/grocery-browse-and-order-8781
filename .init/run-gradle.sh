#!/usr/bin/env sh
# Helper used by CI to invoke Gradle reliably from repo root.
set -e
ROOT_DIR=$(CDPATH= cd -- "$(dirname -- "$0")/.." && pwd)
exec "$ROOT_DIR/gradlew" "$@"
