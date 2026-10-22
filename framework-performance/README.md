# framework-performance

Shared performance assets for every app in this project. Part of the
**Quality Impact Pack**.

## What's here

```
framework-performance/
├── pom.xml                 # packaging=jar, depends on framework-core
├── src/main/java/.../
│   └── LocalPerformanceTarget.java   # local/offline HTTP target helper
├── thresholds.yaml         # provider-neutral advisory performance budgets
├── baseline/               # JSON baseline samples per app (golden numbers)
├── reports/                # threshold evaluation and baseline comparison reports
└── providers/
    ├── gatling-java/       # local offline reference producer (run-gatling-smoke)
    ├── k6/                 # importer only (import-performance-result)
    ├── jmeter/             # importer only (import-performance-result)
    └── jmh/                # placeholder
```

## What is implemented

- Directory structure, README files, baseline JSON examples, and a
  provider-neutral `thresholds.yaml` contract.
- Per-app `apps/<id>/performance/` modules with an opt-in local/offline
  Gatling Java reference smoke command path.
- Shared JDK-only `LocalPerformanceTarget` helper for deterministic
  loopback execution. It starts an in-process HTTP server on `127.0.0.1`
  using an ephemeral port during the smoke run only; no external service or
  stored credential is involved.
- `thresholds.yaml` is advisory-only. It is not enforced by `mvn verify`,
  `qaff verify`, `run-performance-smoke`, `run-gatling-smoke`, or any
  generated CI file.
- `qaff evaluate-quality-impact` reads a `qaff/performance-result/v1` file
  and produces advisory `qaff/threshold-evaluation/v1` and
  `qaff/baseline-comparison/v1` reports. Supports `--strict` gating:
  `OVER_THRESHOLD` metrics and `REGRESSION` baselines fail, while
  `INSUFFICIENT_DATA` and `NOT_EVALUATED` never fail the gate.
- `qaff update-baseline` updates `framework-performance/baseline/<app>.baseline.json`
  from an accepted `qaff/performance-result/v1` file — closing the
  evaluate → accept → update baseline workflow loop. The baseline file is
  overwritten; use git history to review accepted changes.

## Targeting your real system

By default the Gatling smoke runs fully offline against the in-process
`LocalPerformanceTarget` loopback stub — the numbers it produces validate
provider wiring, nothing more. To aim the same simulation at a real
deployment, pass a base URL (system property first, environment variable as
fallback); the loopback stub is then never started:

```bash
mvn -pl apps/<app-id>/performance -am -Pgatling-smoke verify \
  -Dperf.baseUrl=https://your-host.example.internal
# or: export QAFF_PERF_BASE_URL=https://your-host.example.internal
```

If the target requires authentication, provide an `Authorization` header
value (never commit it):

```bash
  -Dperf.authorizationHeader="Bearer <token>"
# or: export QAFF_PERF_AUTHORIZATION_HEADER="Bearer <token>"
```

### Shaping the load without code changes

The generated simulation exposes a basic injection-profile seam (system
property first, environment variable as fallback; defaults reproduce the
one-user smoke):

```bash
mvn -pl apps/<app-id>/performance -am -Pgatling-smoke verify \
  -Dperf.baseUrl=https://your-host.example.internal \
  -Dperf.users=50 \
  -Dperf.rampSeconds=30 \
  -Dperf.repeat=10
# or: QAFF_PERF_USERS / QAFF_PERF_RAMP_SECONDS / QAFF_PERF_REPEAT
```

- `perf.users` (default 1) — concurrent virtual users per run; each app's
  simulation is invoked separately, so different apps can use different
  values.
- `perf.rampSeconds` (default 0) — when > 0, users ramp in linearly over
  the window (`rampUsers(N).during(S)`) instead of all at once.
- `perf.repeat` (default 1) — how many times each user executes the
  scenario.

The generated scenario is still a single `GET /health` — before treating
external-run results as meaningful, edit the simulation under
`apps/<app-id>/performance/src/test/java/.../gatling/` to cover your real
critical path (requests, feeders, checks); richer injection profiles
(`constantUsersPerSecond`, staged ramps, …) remain code-level choices.
Import the produced `simulation.log` with `qaff import-performance-result`
exactly as in the offline path.

## Advisory threshold evaluation contract

Two provider-neutral contracts are defined for runtime use.

### `qaff/performance-result/v1` — normalized provider result

Future provider adapters will normalize native output (Gatling, k6, JMeter,
JMH) into this JSON shape before evaluation:

```
$schema: qaff/performance-result/v1
tool: gatling-java | k6 | jmeter | jmh | unknown
provider: provider-neutral
app: <app-id>
scenario: <scenario-id>
level: smoke | regression | load
generatedAt: <ISO-8601>          # set at runtime, not by qaff generate
source: <path or provider id>
metrics:
  responseTime: { p50Ms, p95Ms, p99Ms }
  errorRate: { percent }
  throughput: { requestsPerSecond }
  sample: { requests, durationSeconds }
valid: true | false
warnings: []
notes: []
```

### `qaff/threshold-evaluation/v1` — advisory evaluation report

Advisory evaluator reads `thresholds.yaml` + one normalized result
and emits this advisory report:

```
$schema: qaff/threshold-evaluation/v1
status: advisory
enforcement: none
app: <app-id>
scenario: <scenario-id>
level: smoke | regression | load
thresholdsVersion: 1
verdict: PASS | OVER_THRESHOLD | INSUFFICIENT_DATA | NOT_EVALUATED
findings:
  - metric: <metric-path>
    actual: <number>
    operator: max | min
    threshold: <number>
    passed: true | false | null
    severity: info | warning
    message: <human-readable>
```

Advisory reports are **non-blocking**: a verdict of `OVER_THRESHOLD` does
not fail `mvn verify`, `qaff verify`, helper scripts, or generated CI.
Pass `--strict` to `qaff evaluate-quality-impact` to turn it into a hard
gate.

## What is NOT implemented yet

- **Default `mvn verify` still does not run Gatling.** Local Gatling smoke is
  explicit-only via `-Pgatling-smoke verify` or helper scripts.
- This local reference smoke is not a benchmark, regression gate, trend
  analysis, or full load suite.
- No other provider runs here: `k6` and `jmeter` are importer-only (via
  `qaff import-performance-result`; k6 is the `ts-npm-playwright` blueprint's
  reference provider) and `jmh` remains an experimental placeholder.
- No **automatic** `qaff/performance-result/v1` emitter: nothing writes
  result files as a side effect of `mvn verify`. Result files are created by
  explicitly importing provider output with `qaff import-performance-result`
  (Gatling `simulation.log`, k6 JSON summary, JMeter `.jtl` adapters) or by
  hand-authoring the v1 JSON.
- No CI gating on `thresholds.yaml` is generated automatically.

## How to read thresholds today

Treat `thresholds.yaml` as the provider-neutral budget contract that
`qaff evaluate-quality-impact` reads. You can tune it now without
changing build behavior. `baseline/` holds the accepted performance
snapshot; update it with `qaff update-baseline` after reviewing results.
`reports/` is the destination for threshold and baseline comparison output.
