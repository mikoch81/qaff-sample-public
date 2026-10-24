Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

Write-Host "=== Running all tests ==="
# Set BROWSER_BINARY if Chrome or Chromium is not on PATH:
# $env:BROWSER_BINARY = "C:\Program Files\Google\Chrome\Application\chrome.exe"
# $env:BROWSER_BINARY = "C:\Program Files\Chromium\Application\chromium.exe"

mvn -B verify

Write-Host ""
Write-Host "=== All tests completed ==="
