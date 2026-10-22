# demo-shop — ios

iOS native Appium smoke test for **demo-shop**. iOS layer.

## Prerequisites (once, macOS only)

- Xcode with an iOS simulator runtime (`xcrun simctl list devices` shows e.g. `iPhone 16`).
- Appium 2 or 3 with the XCUITest driver: `npm i -g appium && appium driver install xcuitest`.
  The first session builds WebDriverAgent, which takes a few minutes.
- A booted simulator (`xcrun simctl boot "iPhone 16"`) or a provisioned device
  (`appium.udid`; real devices also need signing — see the XCUITest driver docs).

## Rehearse with the official sample app

```bash
bash scripts/download-ios-sample.sh              # UIKitCatalog simulator build -> .qaff/samples/
appium &                                         # Appium server on :4723
export QAFF_APPIUM_APP=$PWD/.qaff/samples/UIKitCatalog-iphonesimulator.zip
export QAFF_SMOKE_ACCESSIBILITY_ID=Buttons       # row on UIKitCatalog's first screen
bash scripts/run-ios-smoke.sh
```

## Your own app

Set `appium.app` (path to the simulator `.app` or a zip of it) — or `appium.bundleId` for an
app that is already installed — and `smoke.accessibilityId` (an accessibility identifier on
your first screen) in `src/test/resources/appium-config.properties`, then run
`bash scripts/run-ios-smoke.sh` or
`mvn -B -pl apps/demo-shop/ios -am verify -Dappium.enabled=true`.

Without `-Dappium.enabled=true` the smoke is skipped, so the project's plain
`mvn verify` stays green on Linux / Windows and on machines without a simulator.

## GitHub-hosted macOS runner

The hosted `macos-15` image ships Xcode, iPhone simulators, Node and Maven, so the smoke
runs without self-hosted hardware (one job, manual trigger; adapt the device name to the
image's simulator list):

```yaml
# .github/workflows/ios-smoke.yml
on: { workflow_dispatch: }
jobs:
  ios-smoke:
    runs-on: macos-15
    steps:
      - uses: actions/checkout@v5
      - run: npm i -g appium && appium driver install xcuitest
      - run: xcrun simctl boot "iPhone 16" && xcrun simctl bootstatus "iPhone 16" -b
      - run: bash scripts/download-ios-sample.sh
      - run: (appium --log-level warn > appium.log 2>&1 &) && sleep 5
      - run: bash scripts/run-ios-smoke.sh
        env:
          QAFF_APPIUM_APP: ${{ github.workspace }}/.qaff/samples/UIKitCatalog-iphonesimulator.zip
          QAFF_SMOKE_ACCESSIBILITY_ID: Buttons
      - uses: actions/upload-artifact@v5
        if: always()
        with: { name: ios-smoke-logs, path: appium.log }
```

## Allure results

```
apps/demo-shop/ios/target/allure-results/
```

## Helper script

```bash
# Linux/macOS
bash scripts/run-ios-smoke.sh

# Windows PowerShell
.\scripts\run-ios-smoke.ps1
```
