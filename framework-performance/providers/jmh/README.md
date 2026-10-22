# jmh provider — **experimental placeholder**

> Not implemented. This folder exists as scaffolding only.

JMH (Java Microbenchmark Harness). For micro-benchmarks of pure Java code paths, not for end-to-end load.

## Status

- ❌ No runner configured.
- ❌ No scenarios.
- ❌ Not invoked by `mvn verify` or by any CI step shipped with this framework.

## What lands here later

- A provider-specific project layout (e.g. a JMH benchmark module).
- A small adapter that converts provider results into the JSON shape used
  by `../baseline/<app-id>.baseline.json` so the comparison engine can
  consume them.

Pick **one** provider per workload class — do not run all four.
