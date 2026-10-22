#!/usr/bin/env bash
set -euo pipefail

# Downloads the official Appium iOS sample app (UIKitCatalog, simulator build) to
# rehearse the iOS smoke before your own .app is available. Stored in .qaff/samples/ (git-ignored).
mkdir -p .qaff/samples
curl -fL -o .qaff/samples/UIKitCatalog-iphonesimulator.zip "https://github.com/appium/ios-uicatalog/releases/download/v4.0.5/UIKitCatalog-iphonesimulator.zip"

echo ''
echo 'Downloaded .qaff/samples/UIKitCatalog-iphonesimulator.zip. Run the smoke against it with:'
echo "  export QAFF_APPIUM_APP=$PWD/.qaff/samples/UIKitCatalog-iphonesimulator.zip"
echo '  export QAFF_SMOKE_ACCESSIBILITY_ID=Buttons'
echo '  bash scripts/run-ios-smoke.sh'
