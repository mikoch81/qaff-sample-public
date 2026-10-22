# How to run tests locally

## Overview

This guide shows you how to run tests on your development machine.

## Prerequisites

- Java 21 (or 17 if configured)
- Maven 3.9+
- Chrome or Chromium browser (Selenium Manager installs chromedriver automatically)

## Run all tests

```bash
mvn verify
```

This runs all tests across all modules.

## Run tests for a specific app

```bash
mvn -pl apps/myapp/web-desktop test
mvn -pl apps/myapp/api test
mvn -pl apps/myapp/integration test
```

## Run a specific test

```bash
mvn test -Dtest=LoginTest
mvn test -Dtest=LoginTest#userCanLogin
```

## Run tests by tag

```bash
mvn test -Dgroups=smoke
mvn test -Dgroups=smoke,critical
mvn test -DexcludedGroups=slow
```

## Run web tests only

```bash
bash scripts/run-web-smoke.sh  (Linux/macOS)
.\scripts\run-web-smoke.ps1   (Windows)
```

## Configure your browser

Use a specific browser binary:

```bash
BROWSER_BINARY=/usr/bin/chromium mvn test
BROWSER_BINARY="C:\Program Files\Chromium\chrome.exe" mvn test
```

## View test results

Test reports are in each module:

```
apps/<id>/web-desktop/target/surefire-reports/
apps/<id>/web-desktop/target/allure-results/
```

## Helper scripts

Run provided scripts for common tasks:

```bash
bash scripts/verify-framework.sh
bash scripts/run-all-tests.sh
bash scripts/run-web-smoke.sh
```

See [HOW_TO_USE_GENERATED_SCRIPTS.md](HOW_TO_USE_GENERATED_SCRIPTS.md) for details.
