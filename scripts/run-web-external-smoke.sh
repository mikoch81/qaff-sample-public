#!/usr/bin/env bash
set -euo pipefail

echo '=== Running external web smoke tests ==='

if [ -z "${QAFF_WEB_BASE_URL:-}" ]; then
  echo 'QAFF_WEB_BASE_URL is required for run-web-external-smoke.'
  echo 'Set QAFF_WEB_BASE_URL and re-run this script.'
  exit 1
fi

mvn -B -Pweb-external-smoke -pl apps/demo-shop/web-desktop -am verify -Dweb.baseUrl=${QAFF_WEB_BASE_URL}

echo ''
echo '=== External web smoke tests completed ==='
