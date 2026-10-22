#!/usr/bin/env bash
set -euo pipefail

echo '=== Running web-desktop smoke tests ==='
# Set BROWSER_BINARY if Chrome or Chromium is not on PATH:
# export BROWSER_BINARY=/usr/bin/chromium
# export BROWSER_BINARY=/usr/bin/google-chrome

mvn -B -pl apps/demo-shop/web-desktop -am verify

echo ''
echo '=== Web smoke tests completed ==='
