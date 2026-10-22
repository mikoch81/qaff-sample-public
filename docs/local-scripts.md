# Local scripts

The `scripts/` directory contains cross-platform helper scripts for
local verification. Scripts do not require `qaff` to be installed.

## Scripts

| Script | .ps1 (Windows) | .sh (Linux/macOS) |
| --- | --- | --- |
| verify-framework | `scripts/verify-framework.ps1` | `scripts/verify-framework.sh` |
| run-all-tests | `scripts/run-all-tests.ps1` | `scripts/run-all-tests.sh` |
| run-web-smoke | `scripts/run-web-smoke.ps1` | `scripts/run-web-smoke.sh` |
| run-web-external-smoke | `scripts/run-web-external-smoke.ps1` | `scripts/run-web-external-smoke.sh` |
| run-mobile-appium-smoke | `scripts/run-mobile-appium-smoke.ps1` | `scripts/run-mobile-appium-smoke.sh` |
| run-android-smoke | `scripts/run-android-smoke.ps1` | `scripts/run-android-smoke.sh` |
| run-ios-smoke | `scripts/run-ios-smoke.ps1` | `scripts/run-ios-smoke.sh` |
| run-api-smoke | `scripts/run-api-smoke.ps1` | `scripts/run-api-smoke.sh` |
| run-integration-smoke | `scripts/run-integration-smoke.ps1` | `scripts/run-integration-smoke.sh` |
| run-performance-smoke | `scripts/run-performance-smoke.ps1` | `scripts/run-performance-smoke.sh` |
| run-gatling-smoke | `scripts/run-gatling-smoke.ps1` | `scripts/run-gatling-smoke.sh` |
| run-k6-import-smoke | `scripts/run-k6-import-smoke.ps1` | `scripts/run-k6-import-smoke.sh` |
| run-jmeter-import-smoke | `scripts/run-jmeter-import-smoke.ps1` | `scripts/run-jmeter-import-smoke.sh` |

## Prerequisites

- **Java 21** (or Java 17 if re-generated with `--java 17`).
- **Maven 3.9** or newer on `PATH`.
- **Chrome or Chromium** installed. Selenium Manager downloads
  `chromedriver` automatically on first run (requires network on
  first run only).

## Windows usage

Run from the project root in PowerShell:

```powershell
.\scripts\verify-framework.ps1
.\scripts\run-web-smoke.ps1
.\scripts\run-web-external-smoke.ps1
.\scripts\run-mobile-appium-smoke.ps1
.\scripts\run-android-smoke.ps1
.\scripts\run-ios-smoke.ps1
.\scripts\run-api-smoke.ps1
.\scripts\run-integration-smoke.ps1
.\scripts\run-performance-smoke.ps1
.\scripts\run-gatling-smoke.ps1
.\scripts\run-k6-import-smoke.ps1
.\scripts\run-jmeter-import-smoke.ps1
```

## Linux/macOS usage

Run from the project root in a terminal:

```bash
bash scripts/verify-framework.sh
bash scripts/run-web-smoke.sh
bash scripts/run-web-external-smoke.sh
bash scripts/run-mobile-appium-smoke.sh
bash scripts/run-android-smoke.sh
bash scripts/run-ios-smoke.sh
bash scripts/run-api-smoke.sh
bash scripts/run-integration-smoke.sh
bash scripts/run-performance-smoke.sh
bash scripts/run-gatling-smoke.sh
bash scripts/run-k6-import-smoke.sh
bash scripts/run-jmeter-import-smoke.sh
```

## Chrome / Chromium requirement

Selenium 4 ships Selenium Manager which downloads a matching
`chromedriver` binary automatically. If your system uses Chromium or a
non-default Chrome path, set `BROWSER_BINARY` before running:

### Windows (PowerShell)

```powershell
$env:BROWSER_BINARY = "C:\Program Files\Google\Chrome\Application\chrome.exe"
# or for Chromium:
$env:BROWSER_BINARY = "C:\Program Files\Chromium\Application\chromium.exe"
.\scripts\run-web-smoke.ps1
```

### Linux/macOS

```bash
export BROWSER_BINARY=/usr/bin/chromium
bash scripts/run-web-smoke.sh
```

## Script descriptions

### verify-framework

Runs `mvn -B verify` and then prints a short summary:
- where Surefire reports are located
- where Allure results are located
- a reminder that `qaff verify --project .` (if `qaff` is installed)
  generates `QAFF_HEALTH_REPORT.md`

Example for `demo-shop`:

```
apps/demo-shop/web-desktop/target/allure-results/
```

### run-all-tests

Runs `mvn -B verify` across all modules.

### allure-report

Builds one Allure HTML report for the whole reactor after `mvn verify`: it
collects every module's `target/allure-results` into the root `target/` and
runs `mvn -N allure:report` (the `allure-maven` plugin downloads the Allure
command line from Maven Central on first use).

```bash
# Linux/macOS
bash scripts/allure-report.sh

# Windows PowerShell
.\scripts\allure-report.ps1
```

Open `target/site/allure-maven-plugin/index.html`.

### run-web-smoke

Runs `mvn -B -pl apps/<app>/web-desktop -am verify` for every generated
app. Building with `-am` also compiles the upstream `framework-web` and
`framework-core` modules so you can run this on a clean workspace.

### run-web-external-smoke

Runs web-desktop smoke in external-target mode for apps that declare
`web.target: external`.

Generated apps with external web target: `demo-shop`

This script requires `QAFF_WEB_BASE_URL` and forwards it as
`-Dweb.baseUrl`.

```bash
# Linux/macOS
export QAFF_WEB_BASE_URL=https://example.test
bash scripts/run-web-external-smoke.sh

# Windows PowerShell
$env:QAFF_WEB_BASE_URL = "https://example.test"
.\scripts\run-web-external-smoke.ps1
```

The generated root `pom.xml` also contains an opt-in `web-external-smoke`
profile:

```bash
mvn -B -P web-external-smoke -pl apps/demo-shop/web-desktop -am verify -Dweb.baseUrl=${QAFF_WEB_BASE_URL}
```

Default `mvn verify` remains fixture/local-first.


### run-mobile-appium-smoke

No mobile-appium modules are configured in this project.
The generated `run-mobile-appium-smoke` scripts print a message and exit cleanly.
Add the mobile-appium layer and re-generate to add Appium smoke tests.

### run-android-smoke

No android modules are configured in this project.
The generated `run-android-smoke` scripts print a message and exit cleanly.
Add the android layer and re-generate to add Android native smoke tests.

### run-ios-smoke

Runs the iOS native Appium smoke tests for every app that has an `ios` module:

Generated apps with an `ios` module: `demo-shop`

```bash
# Linux/macOS
bash scripts/run-ios-smoke.sh

# Windows PowerShell
.\scripts\run-ios-smoke.ps1
```

The scripts pass `-Dappium.enabled=true`; without it the smoke is skipped, so plain
`mvn verify` stays green without a simulator. Needs macOS with Xcode, a booted
simulator and Appium with the `xcuitest` driver; `scripts/download-ios-sample` fetches
the official UIKitCatalog build to rehearse with (see the module README, which also has a
GitHub-hosted `macos-15` runner job).

To run only the ios module from the Maven reactor without the helper script:

```bash
mvn -B -pl apps/demo-shop/ios -am verify -Dappium.enabled=true
```

The generated root `pom.xml` also contains an opt-in `ios-smoke` profile:

```bash
mvn -B -P ios-smoke verify
```

### run-api-smoke

Runs the RestAssured API smoke tests for every app that has an `api`
module (Quality Impact Pack):

Generated apps with an `api` module: `demo-shop`

```bash
# Linux/macOS
bash scripts/run-api-smoke.sh

# Windows PowerShell
.\scripts\run-api-smoke.ps1
```

#### API smoke against a real environment

The generated API smoke is offline by default (`api.baseUrl=local` in
`api-config.properties` spins up an in-process `HttpServer` stub). To point
the same smoke at a real base URL, set either of these environment variables
in your shell before running the script. The script forwards them to Maven as
`-D` system properties:

| Environment variable | Maven system property | Purpose |
| --- | --- | --- |
| `QAFF_API_BASE_URL` | `-Dapi.baseUrl` | Override the API base URL (`local` = in-process stub) |
| `QAFF_API_TIMEOUT_SECONDS` | `-Dapi.timeoutSeconds` | Override the request timeout |
| `QAFF_API_HEALTH_PATH` | `-Dapi.healthPath` | GET path expected to answer 200 (default `/health`) |

#### run-api-mock

Rehearse the real-target path without a deployed service: the bundled
dependency-free mock (`scripts/ApiMockServer.java`, run as a JDK
single-file program) serves `GET /health` → 200 on loopback.

```bash
# Linux/macOS — terminal 1, then terminal 2
bash scripts/run-api-mock.sh 8089
QAFF_API_BASE_URL=http://127.0.0.1:8089 bash scripts/run-api-smoke.sh
```

```powershell
# Windows PowerShell
.\scripts\run-api-mock.ps1 8089
$env:QAFF_API_BASE_URL = "http://127.0.0.1:8089"; .\scripts\run-api-smoke.ps1
```

```bash
# Linux/macOS
export QAFF_API_BASE_URL=https://api.example.local
bash scripts/run-api-smoke.sh
```

```powershell
# Windows PowerShell
$env:QAFF_API_BASE_URL = "https://api.example.local"
.\scripts\run-api-smoke.ps1
```

To run only the API smoke modules from the Maven reactor without the
helper script, select the target module explicitly:

```bash
mvn -B -pl apps/demo-shop/api -am verify
```

With runtime overrides:

```bash
mvn -B -pl apps/demo-shop/api -am -Dapi.baseUrl=https://api.example.local -Dapi.timeoutSeconds=15 verify
```

The generated root `pom.xml` also contains an opt-in `api-smoke` profile:

```bash
mvn -B -P api-smoke verify
```

Treat that profile as a manual marker only; API-only targeting still comes
from `-pl apps/<id>/api -am`.

### run-integration-smoke

The Quality Impact Pack is not enabled, so no integration smoke modules exist.
The generated `run-integration-smoke` scripts print a message and exit cleanly.
Re-generate with `packs.qualityImpact: true` to add integration smoke tests.

### run-performance-smoke

No performance modules are configured in this project.
The generated `run-performance-smoke` scripts print a message and exit cleanly.
Enable the performance layer and re-generate to add performance module verification.

### run-gatling-smoke

No Gatling smoke modules are configured in this project.
The generated `run-gatling-smoke` scripts print a message and exit cleanly.
Default `mvn verify` does not run Gatling. Enable the performance layer and re-generate to add local Gatling reference smoke.

### run-k6-import-smoke

No performance modules are configured in this project.
The generated `run-k6-import-smoke` scripts print a message and exit cleanly.
Enable the performance layer and re-generate to add optional k6 import helpers.

### run-jmeter-import-smoke

No performance modules are configured in this project.
The generated `run-jmeter-import-smoke` scripts print a message and exit cleanly.
Enable the performance layer and re-generate to add optional JMeter import helpers.

## Relation to qaff verify

The scripts do not call `qaff`. They are standalone Maven wrappers. If
you have `qaff` installed, you can run a full health check from outside
the project:

```bash
qaff verify --project <path-to-this-project>
```

`qaff verify` reads `qa-factory.lock.json`, checks module presence,
validates dependency direction, and optionally writes
`QAFF_HEALTH_REPORT.md`.
