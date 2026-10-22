# How to update baseline

## Overview

Baselines record "expected" performance for future comparisons. This guide explains when and how to update baselines safely.

## When to Update

Update the baseline after:
1. ✅ All tests pass
2. ✅ Quality gates show good results (no failures)
3. ✅ You want to mark this as the new "normal" performance

Do **not** update after known performance regressions or failures.

## How to Update

### 1. Verify results look good

```bash
qaff evaluate-quality-impact --project .
```

Review the report. All gates should pass before promoting.

### 2. Update the baseline

```bash
qaff update-baseline --project .
```

This command:
- Copies current results to `framework-performance/baseline/<app-id>.baseline.json`
- Marks those files as "managed" (won't be edited manually)
- Next evaluation will compare against this new baseline

### 3. Commit the new baseline

```bash
git add framework-performance/baseline/
git commit -m "perf: update baseline after optimization"
```

## Baseline Files

Baselines live in:

```
framework-performance/
├── baseline/
│   ├── myapp.baseline.json   ← Current baseline
│   └── ...
└── reports/
    ├── myapp.result.json      ← Latest test run results
    └── ...
```

## Safety

- **Baselines are managed files** — Do not edit by hand. Use `qaff update-baseline`.
- **Compare before promoting** — Run `qaff evaluate-quality-impact` first to see what's changing.
- **Commit baselines** — Track baseline changes in version control.

## Troubleshooting

**"Result file not found"** — Run tests first:
```bash
mvn verify
```

**"Baseline is older than current results"** — Normal. The baseline is the "expected" performance from a previous run. The report shows how current results compare.

## Next Steps

- See [HOW_TO_USE_QUALITY_IMPACT.md](HOW_TO_USE_QUALITY_IMPACT.md) for evaluation workflow
- See [docs/generated-architecture.md](generated-architecture.md) for Performance layer details
