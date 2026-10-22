# AI API Test Authoring

This is static guidance for AI-assisted authoring. It is not a runtime.

## Where to add API tests

- API tests: `apps/<id>/api/src/test/java` (when Quality Impact Pack is enabled)
- API resources/fixtures: `apps/<id>/api/src/test/resources`
- Shared API helpers: existing shared modules under `framework-api`

## Authoring rules

- Keep tests deterministic and explicit about expected status/body.
- Avoid external network dependency in smoke-level checks.
- Reuse existing helper/support classes before adding new utility code.

Jira import is future scope and not implemented in this phase.
Zephyr execution write is available through the MCP tool in dry-run
mode by default, with optional live mode when explicitly enabled.

## Quality Impact reminders

- `qaff evaluate-quality-impact` and `qaff update-baseline` require
  `framework-performance/reports/<app-id>.result.json` files.
- `qaff verify` is structural health check only.

## Validate

- `qaff verify --project .`
- `mvn -B verify`
- optional: `scripts/run-api-smoke.*`
