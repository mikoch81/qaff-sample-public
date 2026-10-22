# gatling-java provider — local offline reference producer

> The Gatling Java reference smoke runs locally and offline through `scripts/run-gatling-smoke` (opt-in; default `mvn verify` does not run it). This folder holds provider notes only.

Gatling 3.x with the Java DSL. Best for HTTP-heavy scenarios with rich reporting out of the box.

## Status

- ✅ Local/offline reference smoke is available through app performance modules and `-Pgatling-smoke verify` (opt-in only).
- ✅ One smoke simulation per performance app lives in `apps/<id>/performance/` (not in this folder).
- ⚠️ Not invoked by default `mvn verify`; only the opt-in `ci.<provider>.performanceRun` CI step runs it.

The Gatling Java path is intentionally tiny and local. It is not a
benchmark, gate, or trend system.

Pick **one** provider per workload class — do not run all four.
