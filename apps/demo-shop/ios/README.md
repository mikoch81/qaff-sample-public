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
runs without self-hosted hardware (one job, manual trigger). The first XCUITest session
builds WebDriverAgent (about 4 minutes), longer than the Java client's HTTP timeout — the
warm-up step below opens and closes that first session with `curl`, so the Maven run then
starts in seconds. Several runtimes ship the same device name, so the job pins one
simulator by UDID:

```yaml
# .github/workflows/ios-smoke.yml
on: { workflow_dispatch: }
jobs:
  ios-smoke:
    runs-on: macos-15
    timeout-minutes: 40
    steps:
      - uses: actions/checkout@v5
      - uses: actions/setup-java@v6
        with: { distribution: temurin, java-version: "21", cache: maven }
      - run: npm i -g appium && appium driver install xcuitest
      - id: sim
        run: |
          UDID=$(xcrun simctl list devices available -j | jq -r '[.devices[] | .[] | select(.name == "iPhone 16")][0].udid')
          echo "udid=$UDID" >> "$GITHUB_OUTPUT"
          xcrun simctl boot "$UDID" && xcrun simctl bootstatus "$UDID" -b
      - run: bash scripts/download-ios-sample.sh
      - run: |
          (appium --log-level info > appium.log 2>&1 &)
          for i in $(seq 1 30); do curl -sf localhost:4723/status >/dev/null && break; sleep 2; done
      - name: Warm up WebDriverAgent
        run: |
          SID=$(curl -sS -X POST localhost:4723/session -H 'Content-Type: application/json' -d '{"capabilities":{"alwaysMatch":{"platformName":"iOS","appium:automationName":"XCUITest","appium:udid":"${{ steps.sim.outputs.udid }}","appium:app":"${{ github.workspace }}/.qaff/samples/UIKitCatalog-iphonesimulator.zip"}}}' | jq -r '.value.sessionId // empty')
          test -n "$SID" || { tail -50 appium.log; exit 1; }
          curl -sS -X DELETE "localhost:4723/session/$SID" >/dev/null
      - run: bash scripts/run-ios-smoke.sh
        env:
          QAFF_APPIUM_UDID: ${{ steps.sim.outputs.udid }}
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
