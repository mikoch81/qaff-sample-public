Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

# Downloads the official Appium iOS sample app (UIKitCatalog, simulator build) to
# rehearse the iOS smoke before your own .app is available. Stored in .qaff/samples/ (git-ignored).
New-Item -ItemType Directory -Force -Path .qaff/samples | Out-Null
Invoke-WebRequest -Uri "https://github.com/appium/ios-uicatalog/releases/download/v4.0.5/UIKitCatalog-iphonesimulator.zip" -OutFile .qaff/samples/UIKitCatalog-iphonesimulator.zip

Write-Host ""
Write-Host "Downloaded .qaff/samples/UIKitCatalog-iphonesimulator.zip. Run the smoke against it with:"
Write-Host "  $env:QAFF_APPIUM_APP = \"$PWD/.qaff/samples/UIKitCatalog-iphonesimulator.zip\""
Write-Host "  $env:QAFF_SMOKE_ACCESSIBILITY_ID = \"Buttons\""
Write-Host "  ./scripts/run-ios-smoke.ps1"
