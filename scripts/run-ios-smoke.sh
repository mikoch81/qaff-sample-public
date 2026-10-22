#!/usr/bin/env bash
set -euo pipefail

echo '=== Running iOS native smoke ==='

mvn -B -pl apps/demo-shop/ios -am verify -Dappium.enabled=true

echo ''
echo '=== iOS native smoke completed ==='
