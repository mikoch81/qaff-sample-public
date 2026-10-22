# JMeter Provider

## Overview

This folder contains documentation for integrating Apache JMeter performance test results
with the qaff performance evaluation workflow.

## Current Status

Phase 7.1 ships a public importer command for normalizing local JMeter JTL/CSV files
into `qaff/performance-result/v1` JSON.

Phase 7.2 ships project-aware mode for the importer: omit `--output` and use
`--project . ` to write directly to the standard framework reports path
(`framework-performance/reports/<app-id>.result.json`).

Phase 7.4 ships manifest-driven batch mode in project scope:
use `--manifest <path>` with `--project` and `--provider jmeter` to import
multiple app results in one command.

Generated helper scripts can import conventional per-app files from
`framework-performance/providers/jmeter/input/<app-id>.jtl`.

## Quick Start

### Step 1: Run JMeter tests locally or in CI

```bash
jmeter -n -t your-tests.jmx -l results.jtl
```

### Step 2: Ensure CSV format

- JMeter writes `results.jtl` in CSV format by default
- Ensure the header row includes: `timeStamp,elapsed,label,responseCode,success`

### Step 3: Import results using project mode (Phase 7.2)

```bash
qaff import-performance-result \
  --project . \
  --provider jmeter \
  --input results.jtl \
  --app demo-ecommerce \
  --level smoke \
  --scenario homepage
```

Or run the generated helper script to import all detected per-app files:

```bash
# Linux/macOS
bash scripts/run-jmeter-import-smoke.sh

# Windows PowerShell
.\scripts\run-jmeter-import-smoke.ps1
```

Or use explicit file mode (Phase 7.1):

```bash
qaff import-performance-result --provider jmeter --input results.jtl --app demo-ecommerce --level smoke --scenario homepage --output framework-performance/reports/demo-ecommerce.result.json
```

Or use manifest batch mode in project scope (Phase 7.4):

```bash
qaff import-performance-result \
  --project . \
  --provider jmeter \
  --manifest ./results/import-results.json \
  --json
```

### Step 4: Evaluate quality impact as a separate next step

```bash
qaff evaluate-quality-impact --project .
```

### Step 5: Update baseline only after reviewed acceptance

```bash
qaff update-baseline --project .
```

## Tool Compatibility

- JMeter 3.x: CSV format covered by the adapter contract
- JMeter 4.x: CSV format covered by the adapter contract
- JMeter 5.x: CSV format covered by the adapter contract
- JMeter 6.x: Future validation scope

## CSV Format Reference

See the QA Framework Factory repository document `docs/jmeter-adapter-contract.md`
for the complete mapping from JMeter JTL CSV fields to qaff/performance-result/v1.

## Notes

- Results are **deterministic**: no system-clock timestamps injected by the adapter
- Multi-threaded samples are aggregated into a single result
- Samples with `success=false` or HTTP 4xx/5xx response codes are treated as failures by the parser prototype
- Missing optional JMeter fields (e.g. `sentBytes`, `Latency`) are handled gracefully

## Project Mode Validation (Phase 7.2)

When using `--project`, the importer validates:
- Project directory exists
- `qa-factory.lock.json` is present and valid
- Quality Impact Pack is enabled (`packs.qualityImpact: true`)
- Target app exists in the lock file
- Target app has the performance layer

For Phase 7.4 manifest batch mode, the importer additionally validates:
- Manifest JSON shape (`schemaVersion: 1`, `provider: jmeter`, non-empty `imports[]`)
- One import entry per app (no duplicate app entries)
- Every import input file exists and is parseable
- All validations pass before writing any output file

## Future Scope

- Additional providers (k6, JMH, Gatling)
- Directory-scan or pattern-based batch discovery (without explicit manifest)
- Native JMeter scenario definitions in generated projects
- Auto-execution of JMeter in CI
- Optional auto-trigger of evaluate-quality-impact after successful import
