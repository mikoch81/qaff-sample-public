#!/usr/bin/env bash
set -euo pipefail

echo '=== Running all tests ==='
# Set BROWSER_BINARY if Chrome or Chromium is not on PATH:
# export BROWSER_BINARY=/usr/bin/chromium
# export BROWSER_BINARY=/usr/bin/google-chrome

mvn -B verify

echo ''
echo '=== All tests completed ==='
