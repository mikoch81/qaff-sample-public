Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

Write-Host "=== Verifying framework ==="
Write-Host ""
# Set BROWSER_BINARY if Chrome or Chromium is not on PATH:
# $env:BROWSER_BINARY = "C:\Program Files\Google\Chrome\Application\chrome.exe"
# $env:BROWSER_BINARY = "C:\Program Files\Chromium\Application\chromium.exe"

mvn -B verify

Write-Host ""
Write-Host "=== Maven verification completed ==="
Write-Host "Surefire reports : **/target/surefire-reports/"
Write-Host "Allure results   :"
Write-Host "  apps/demo-shop/web-desktop/target/allure-results/"
Write-Host ""
Write-Host "To run a full health check (requires qaff installed externally):"
Write-Host "  qaff verify --project ."
