Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

Write-Host "=== Running web-desktop smoke tests ==="
# Set BROWSER_BINARY if Chrome or Chromium is not on PATH:
# $env:BROWSER_BINARY = "C:\Program Files\Google\Chrome\Application\chrome.exe"
# $env:BROWSER_BINARY = "C:\Program Files\Chromium\Application\chromium.exe"

mvn -B -pl apps/demo-shop/web-desktop -am verify

Write-Host ""
Write-Host "=== Web smoke tests completed ==="
