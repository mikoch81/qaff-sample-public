#!/usr/bin/env bash
set -euo pipefail

echo '=== Verifying framework ==='
echo ''
# Set BROWSER_BINARY if Chrome or Chromium is not on PATH:
# export BROWSER_BINARY=/usr/bin/chromium
# export BROWSER_BINARY=/usr/bin/google-chrome

mvn -B verify

echo ''
echo '=== Maven verification completed ==='
echo 'Surefire reports : **/target/surefire-reports/'
echo 'Allure results   :'
echo "  apps/demo-shop/web-desktop/target/allure-results/"
echo ''
echo 'To run a full health check (requires qaff installed externally):'
echo '  qaff verify --project .'
