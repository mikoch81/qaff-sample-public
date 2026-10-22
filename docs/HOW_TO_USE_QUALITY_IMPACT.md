# How to use the Quality Impact Pack

## Overview

The Quality Impact Pack provides a structured way to measure and track performance and quality across test runs. This guide explains how to use it.

## Prerequisites

1. **Produce a provider result** — run a performance provider and capture its
   native output. Note: default `mvn verify` does **not** run any load tool
   and does **not** produce result files.
2. **Import it** — `qaff import-performance-result` normalizes provider
   output (Gatling `simulation.log`, k6 JSON summary, JMeter `.jtl`) into
   `framework-performance/reports/<app-id>.result.json`
   (`qaff/performance-result/v1`).
3. **Set thresholds** — Define quality gates in `framework-performance/thresholds.yaml`.

## Workflow

### 1. Produce and import a result file

The generated, fully offline path (in-process loopback target, no network):

```bash
bash scripts/run-gatling-smoke.sh
qaff import-performance-result --project . --provider gatling \
  --input "$(ls -t apps/<app-id>/performance/target/gatling/*/simulation.log | head -1)" \
  --app <app-id> --level smoke --scenario smoke
```

To aim the same simulation at **your real system** instead of the loopback
stub, pass `-Dperf.baseUrl` (and optionally
`-Dperf.authorizationHeader`); shape the load with `-Dperf.users`,
`-Dperf.rampSeconds` and `-Dperf.repeat` — see
`framework-performance/README.md` ("Targeting your real system").

Results produced externally (k6, JMeter) are imported the same way with
`--provider k6` / `--provider jmeter`. You can also hand-author a
`qaff/performance-result/v1` JSON file directly.

### 2. Evaluate Quality Impact

```bash
qaff evaluate-quality-impact --project .
```

This compares current results against the baseline and reports:
- ✅ Passed thresholds
- ⚠️  Advisory (informational only)
- ❌ Failed strict gates (optional; disabled by default)

### 3. Update baseline (once per release)

When results are good and you want to promote them:

```bash
qaff update-baseline --project .
```

This stores the current results as the new baseline for future comparisons.

## Strict Gates

By default, gates are **advisory** (informational). To fail CI if gates fail:

1. Export the config and enable the gate in it:
   ```bash
   qaff export-config --project . --output qa-factory.yaml
   ```
   ```yaml
   ci:
     jenkins:
       strictQualityGate: true
   ```

2. Regenerate — `qaff update` renders from the lock, so the edited config must go through `qaff create` (manual, review-first; see `docs/GENERATED_FILE_OWNERSHIP.md`):
   ```bash
   qaff create --config qa-factory.yaml --output <fresh-dir>
   # copy the regenerated CI definition, qa-factory.lock.json and .qaff/managed-base/ into this project
   qaff update --project . --dry-run
   ```
   `qaff update` never overwrites an existing baseline.

3. Now gates will block the pipeline if thresholds are exceeded.

## Understanding Reports

`qaff evaluate-quality-impact` outputs:
- **Pass:** Metric within threshold
- **Advisory:** Metric outside threshold but not a gate block (informational)
- **Fail:** Metric outside threshold and gate is strict (blocks pipeline)

## Next Steps

- See [HOW_TO_UPDATE_BASELINE.md](HOW_TO_UPDATE_BASELINE.md) for baseline management
- See [docs/generated-architecture.md](generated-architecture.md) for Performance layer details
