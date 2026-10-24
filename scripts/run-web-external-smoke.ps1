Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

Write-Host "=== Running external web smoke tests ==="

if (-not $env:QAFF_WEB_BASE_URL) {
  Write-Host "QAFF_WEB_BASE_URL is required for run-web-external-smoke."
  Write-Host "Set QAFF_WEB_BASE_URL and re-run this script."
  exit 1
}

mvn -B -Pweb-external-smoke -pl apps/demo-shop/web-desktop -am verify -Dweb.baseUrl=$($env:QAFF_WEB_BASE_URL)

Write-Host ""
Write-Host "=== External web smoke tests completed ==="
