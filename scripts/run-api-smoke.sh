#!/usr/bin/env bash
set -euo pipefail

echo '=== Running API smoke tests ==='

# Optional runtime overrides forwarded as -D system properties.
# Set QAFF_API_BASE_URL or QAFF_API_TIMEOUT_SECONDS in your shell to
# point API smoke at a real environment instead of the offline stub.
MAVEN_OVERRIDES=()
if [ -n "${QAFF_API_BASE_URL:-}" ]; then
  MAVEN_OVERRIDES+=("-Dapi.baseUrl=${QAFF_API_BASE_URL}")
fi
if [ -n "${QAFF_API_TIMEOUT_SECONDS:-}" ]; then
  MAVEN_OVERRIDES+=("-Dapi.timeoutSeconds=${QAFF_API_TIMEOUT_SECONDS}")
fi

mvn -B -pl apps/demo-shop/api -am verify "${MAVEN_OVERRIDES[@]}"

echo ''
echo '=== API smoke tests completed ==='
