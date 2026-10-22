# Performance strategy

This document explains the **levels** at which performance is exercised
in this generated framework, and where advisory budgets live. Phase 3.7
adds a provider-neutral threshold model; default `mvn verify` still
does not run provider load or enforce thresholds (see
[`../framework-performance/README.md`](../framework-performance/README.md)).

## Apps covered

- No generated app currently includes the `performance` layer.

## Levels

| Level | Concurrency | Cadence | Enforcement today | Owner |
| --- | --- | --- | --- | --- |
| **Smoke** | 1 virtual user, 1 flow | explicit local run today; future PR step | advisory only | dev |
| **Regression** | low (e.g. 10–50 VUs) | future nightly run | advisory only | QA |
| **Load** | full target | future on-demand capacity review | advisory only | SRE / perf engineer |
| **Soak / Stress** | sustained or beyond target | future on-demand investigation | not modeled yet | SRE / perf engineer |

The first two levels are about **regression detection** — catching the
day a refactor doubles the p95. The last two are about **capacity** —
answering "can we serve N users".

## Where the numbers live

- **Budgets** (advisory, provider-neutral): [`../framework-performance/thresholds.yaml`](../framework-performance/thresholds.yaml).
- **Baselines** (current accepted): [`../framework-performance/baseline/<app>.baseline.json`](../framework-performance/baseline/).
- **Trend reports** (future): `../framework-performance/reports/`.

## Provider choice

The framework scaffolds four providers under
`../framework-performance/providers/` (Gatling Java, k6, JMeter, JMH).
Pick **one per workload class** — running multiple providers against the
same scenario is a maintenance burden, not a feature. JMH is reserved for
JVM micro-benchmarks; do not use it for end-to-end load.

## Advisory threshold evaluation contract (Phase 3.8)

Phase 3.8 defines two provider-neutral contracts. Neither is evaluated
or emitted today — this is docs-and-contract only.

The intended future flow is:

```
provider output  →  qaff/performance-result/v1  →  advisory evaluator
    ↓
 qaff/threshold-evaluation/v1  (status: advisory, enforcement: none)
    ↓
reports/<app>.<scenario>.threshold-evaluation.json  (non-blocking)
```

Possible verdicts: `PASS`, `OVER_THRESHOLD`, `INSUFFICIENT_DATA`, `NOT_EVALUATED`.

Advisory reports are non-blocking. A future explicit gate phase will opt
in to enforcement only after provider adapters and the evaluator exist.

## What is NOT in scope for Phase 3.8

- Default provider execution during `mvn verify`.
- Threshold enforcement during `mvn verify`, `qaff verify`,
  `run-performance-smoke`, `run-gatling-smoke`, or generated CI.
- k6/JMeter/JMH runtime execution.
- Provider adapter or result parser.
- Automated baseline-vs-current comparison.
- Performance-aware merge gates.
- Threshold evaluation report generation (contracts defined, emitter future).

The scaffolding is in place so the upgrade is additive: implement a
provider adapter that emits `qaff/performance-result/v1`, wire the
advisory evaluator to read `thresholds.yaml`, and write
`qaff/threshold-evaluation/v1` reports into `reports/`.
