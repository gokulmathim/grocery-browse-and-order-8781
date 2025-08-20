#!/usr/bin/env sh
# CI entrypoint that ensures Gradle runs from this workspace root.
set -e
DIR="$(CDPATH= cd -- "$(dirname -- "$0")/.." && pwd)"
exec "$DIR/gradlew" "$@"
