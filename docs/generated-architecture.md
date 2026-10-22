# Generated framework — architecture

## Modules

| Module | Purpose | Allowed dependencies |
| --- | --- | --- |
| `framework-core` | Config + classpath helpers. No Selenium, no JUnit runtime. | (none, leaf) |
| `framework-web-playwright` | Playwright (Java) session factory, base page, base web test. | `framework-core` |
| `apps/<id>/web-desktop` | App-specific page objects + smoke tests. | `framework-web-playwright` (test scope) |

## Quality Impact Pack (enabled)

| Module | Purpose | Allowed dependencies |
| --- | --- | --- |
| `framework-performance` | Shared local/offline performance helper + baseline/reports/provider placeholders. `packaging=jar`. | `framework-core` |
| `apps/<id>/api` | RestAssured API smoke for the app. | `framework-core` (test scope) |
| `apps/<id>/integration` | Placeholder integration test module. | `framework-core` (test scope) |
| `apps/<id>/performance` | Placeholder + local Gatling reference smoke wiring for the app. | `framework-performance` (test scope) |

The Quality Impact Pack adds a tiny local/offline Gatling Java reference
smoke path behind explicit opt-in commands. Default `mvn verify` does not
execute Gatling. k6 and JMeter are importer-only (via
`qaff import-performance-result`; k6 is the `ts-npm-playwright` blueprint's
reference provider); JMH remains an experimental placeholder. See
[`docs/performance-strategy.md`](performance-strategy.md) and
[`docs/quality-gates.md`](quality-gates.md) for the levels and gates.

## Dependency direction

```
apps/*  ──►  framework-web-playwright  ──►  framework-core
```

Reverse arrows are forbidden by design. Apps may not depend on each
other. The post-generation validator that ships with `qaff create`
asserts these rules against the actual POMs and fails generation if any
rule is violated.

## Web engine seam (enabled)

Apps choose their web engine per app via `web.engine` in the qaff
config (`selenium` — the default — or `playwright`). A playwright app's
web modules (`web-desktop` and `web-mobile`) depend on
`framework-web-playwright` instead of `framework-web`;
the two engine modules mirror each other's shape (same sub-packages, same
class names) and never depend on each other — the engine-neutral pieces
(WebConfig, retry, suite tags) are deliberately duplicated per engine so
each module stays self-contained. Each engine module is generated only when
at least one app uses that engine — an all-playwright project contains no
`framework-web` at all, and vice versa.

## Base package

Everything lives under `com.example.clientdemopublicqa`. Each app gets its own
sub-package: `com.example.clientdemopublicqa.apps.<sanitized-app-id>.<sanitized-layer>`.

## Generation manifest

`qa-factory.lock.json` records the deterministic generation contract:
generator and blueprint versions, Java version, effective app/layer list,
enabled packs, CI flags, strict quality gate flags, and the Phase 5.0B
`generatedFiles` managed-file snapshot. The snapshot stores SHA-256 hashes
for generated files (excluding the lock itself) so `qaff update --dry-run`
can classify changed files as `rendererDrifted`, `locallyModified`, or
`conflictCandidates`, and report `obsolete` files (recorded in the
snapshot but no longer generated for the current configuration) —
`qaff update --apply` and the remove commands delete those only when they
are unmodified since generation. Current lifecycle commands also maintain
`.qaff/managed-base/`, a content-addressed text base store used only by
`qaff update --apply --merge-conflicts`. This metadata does not make the
generated project depend on QA Framework Factory at runtime.
