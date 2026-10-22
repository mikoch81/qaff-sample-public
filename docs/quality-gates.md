# Quality gates

How "is this build good enough" is decided in this generated framework.
Phase 3.7 wires the **functional** gates and ships a provider-neutral
performance threshold model; performance enforcement lands in a later phase.

## Functional gates (active today)

| Gate | What it checks | Where | Blocks merge? |
| --- | --- | --- | --- |
| Compilation | Every module builds against the configured Java target. | `mvn -B verify` | ✅ |
| Web smoke | Each app's `web-desktop` smoke test passes against its local HTML fixture. | `apps/<id>/web-desktop` | ✅ |
| API smoke | Each app's `api` smoke test (RestAssured against an in-process stub) passes. | `apps/<id>/api` | ✅ |
| Dependency direction | `framework-*` never depends on `apps/*`; apps never depend on each other. | `maven-enforcer-plugin` in `mvn validate` / `verify`, plus the `qaff create` / `qaff verify` validator | ✅ |

## Performance gates (designed, not enforced)

| Level | Budget source | Status |
| --- | --- | --- |
| Smoke | `framework-performance/thresholds.yaml` -> `defaults.smoke` | advisory only; no build enforcement |
| Regression | `framework-performance/thresholds.yaml` -> `defaults.regression` | advisory only; comparator not shipped |
| Load | `framework-performance/thresholds.yaml` -> `defaults.load` | advisory only; report target only |

Today, `thresholds.yaml` is not enforced by `mvn verify`, `qaff verify`,
`run-performance-smoke`, `run-gatling-smoke`, or any generated CI file.
When a future provider runner and evaluator land, they will read
`thresholds.yaml` + a normalized `qaff/performance-result/v1` result,
produce a `qaff/threshold-evaluation/v1` advisory report with verdict
`PASS | OVER_THRESHOLD | INSUFFICIENT_DATA | NOT_EVALUATED`, and write
non-blocking reports into `framework-performance/reports/`.

## Gates explicitly not shipped yet

- Quality Impact Pack — trend / regression reporting on top of historical runs.
- Coverage gate (line/branch).
- Mutation testing gate.
- Static analysis gate (SpotBugs, PMD).
- Accessibility gate.

These are tracked in the project roadmap, not silently dropped.
