# k6 provider — importer

> Pre-produced k6 results are normalized by `qaff import-performance-result --provider k6`; this blueprint does not run k6 itself (the `ts-npm-playwright` blueprint uses k6 as its reference provider). This folder holds provider notes only.

Grafana k6. JavaScript scripts, great for cloud / containerised execution and Prometheus integration.

## Status

- ✅ Import adapter is available via `qaff import-performance-result --provider k6` and generated `run-k6-import-smoke` helper scripts.
- ❌ No k6 scripts in this blueprint — bring your own and import their results.
- ⚠️ Optional import helper scripts can normalize pre-produced files; no generated k6 execution is shipped.

In project mode, results land in `framework-performance/reports/<app-id>.result.json` and feed
`qaff evaluate-quality-impact` like any other provider.

Pick **one** provider per workload class — do not run all four.
