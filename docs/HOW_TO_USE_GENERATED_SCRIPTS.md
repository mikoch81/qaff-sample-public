# How to use generated scripts

## Overview

Helper scripts under `scripts/` make common tasks easier. They work on Windows (PowerShell) and Linux/macOS (bash).

## Available Scripts

| Script | Purpose |
|--------|---------|
| `verify-framework` | Run full `mvn -B verify` |
| `run-all-tests` | Same as `verify-framework` |
| `run-web-smoke` | Run web-desktop smoke tests |
| `run-api-smoke` | Run API smoke tests |
| `run-integration-smoke` | Run integration smoke tests |
| `run-performance-smoke` | Validate performance module (no execution) |
| `run-gatling-smoke` | Run Gatling performance reference smoke |

## Usage

### Windows (PowerShell)

```powershell
.\scripts\verify-framework.ps1
.\scripts\run-web-smoke.ps1
.\scripts\run-api-smoke.ps1
```

### Linux/macOS (bash)

```bash
bash scripts/verify-framework.sh
bash scripts/run-web-smoke.sh
bash scripts/run-api-smoke.sh
```

## Notes

- Scripts do **not** require `qaff` to be installed
- They run Maven commands with safe defaults
- Performance scripts are opt-in; Gatling does not run by default

## Customization

Scripts are generated files and should not be edited. Customize test execution via:
- Maven profiles in `pom.xml`
- Tags and filters (see [HOW_TO_USE_JUNIT5.md](HOW_TO_USE_JUNIT5.md))
- Test class and method names
