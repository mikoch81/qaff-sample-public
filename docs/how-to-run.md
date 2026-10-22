# How to run

## Build & test

```bash
mvn verify
```

This runs Surefire across every module. The web-desktop smoke test starts
Chrome (or Chromium) headlessly, opens a local HTML fixture and asserts the
page renders.

## Helper scripts

Cross-platform scripts are available in `scripts/` for common local tasks:

| Script | What it does |
| --- | --- |
| `verify-framework` | Runs `mvn -B verify` and prints a summary |
| `run-all-tests` | Runs `mvn -B verify` across all modules |
| `run-web-smoke` | Runs web-desktop smoke tests per app |
| `run-web-mobile-smoke` | Runs web smoke tests under Chrome mobile-device emulation (apps with the `web-mobile` layer) |
| `run-api-smoke` | Runs API smoke tests per app (Quality Impact Pack only) |
| `run-integration-smoke` | Runs integration smoke tests per app (Quality Impact Pack only) |
| `run-performance-smoke` | Validates performance module scaffolding per app (Quality Impact Pack only) |
| `run-gatling-smoke` | Runs local/offline Gatling Java reference smoke per performance app (opt-in) |
| `run-k6-import-smoke` | Imports optional k6 summary files into `framework-performance/reports/<app>.result.json` |
| `run-jmeter-import-smoke` | Imports optional JMeter JTL files into `framework-performance/reports/<app>.result.json` |

`run-performance-smoke` validates scaffolding/module wiring only.
`run-gatling-smoke` is explicit opt-in provider smoke. Default
`mvn verify` does not run Gatling.
`run-k6-import-smoke` and `run-jmeter-import-smoke` are optional import
helpers for existing provider outputs. They do not execute provider workloads.

Windows (PowerShell):

```powershell
.\scripts\verify-framework.ps1
.\scripts\run-web-smoke.ps1
```

Linux/macOS (bash):

```bash
bash scripts/verify-framework.sh
bash scripts/run-web-smoke.sh
```

Scripts do **not** require `qaff` to be installed. See
[`docs/local-scripts.md`](local-scripts.md) for details.

## Java version

Generated with target Java **21** (`<maven.compiler.release>`).
Re-generate with `qaff create --java 17` for the compatibility target.

## Browser

Selenium 4 includes Selenium Manager — chromedriver is fetched automatically
on first run. If your system uses Chromium rather than Google Chrome, point
the framework at the binary:

```bash
BROWSER_BINARY=/usr/bin/chromium mvn verify
```

## Allure

Raw results land under each test module:

```
apps/<app>/web-desktop/target/allure-results/
```

Render with the Allure CLI:

```bash
allure serve apps/<app>/web-desktop/target/allure-results
```
