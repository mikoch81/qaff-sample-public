# CI / CD strategy

This generated framework wires **GitHub Actions** (Phase 1.9) as the runner.

## Generated runners

| Runner | Entry point | Notes |
| --- | --- | --- |
| GitHub Actions (Phase 1.9) | [`../.github/workflows/qaff-ci.yml`](../.github/workflows/qaff-ci.yml) | Runs on `pull_request` and `workflow_dispatch`. Selected app + layer via dispatch inputs. |

## GitHub Actions workflow shape

A single workflow at `.github/workflows/qaff-ci.yml`:

- **on `pull_request`** — runs the full Maven reactor
  (`mvn -B verify`).
- **on `workflow_dispatch`** — accepts `application`, `layer`,
  `environment`, `suite`, `browser` and `headless` inputs. When
  both `application` and `layer` are concrete values (not `all`),
  the workflow narrows the build to
  `mvn -B -pl apps/<application>/<layer> -am verify`.

Surefire reports, Allure raw results and (under QI)
`framework-performance/reports/` are uploaded as workflow artifacts.

See [`github-actions.md`](github-actions.md) for prerequisites,
manual-dispatch instructions and current limitations.

## What feeds which gate

| Gate | Source | Where it lives | Status today |
| --- | --- | --- | --- |
| Functional smoke | JUnit 5 + Selenium (web-desktop) / RestAssured (api) | `apps/<id>/<layer>/target/surefire-reports/` | ✅ active |
| Allure trend | Allure raw results | `apps/<id>/<layer>/target/allure-results/` | ✅ archived; report rendering left to the Allure consumer |
| Dependency direction | `maven-enforcer-plugin` + qaff post-generation validator | every `mvn validate` / `verify` (incl. CI) and `qaff create` / `qaff verify` | ✅ active |
| Performance trend | Quality Impact Pack outputs (`framework-performance/reports/`) | Archived if present | ⚠️ documented only — no real Gatling / k6 / JMeter / JMH execution wired |

## Out of scope today

- **Zephyr Scale Cloud sync is opt-in only.** It is enabled only for explicitly configured CI providers and requires user-managed credentials/environment variables.
- **No CI-wired performance provider execution.** The `performance` layer module
  runs its JUnit 5 placeholder under default CI. The local Gatling reference
  smoke is explicit opt-in via `run-gatling-smoke` or direct Maven.
- **No Jenkins** in this project. Re-run `qaff create` with `ci.jenkins.enabled: true` to scaffold it.
- **No Azure Pipelines** in this project. Re-run `qaff create` with `ci.azurePipelines.enabled: true` to scaffold it.
- **No GitLab CI** in this project. Re-run `qaff create` with `ci.gitlabCi.enabled: true` to scaffold it.
- **No deployment.** Generated runners run tests; they do not push
  artefacts or deploy applications.
- **No inline secrets.** Generated CI files contain no credential values.
  The opt-in Zephyr sync step reads user-managed CI secrets / environment
  variables (`ZEPHYR_BASE_URL`, `ZEPHYR_TOKEN`); nothing is stored in
  generated files. The opt-in run notification step reads SLACK_WEBHOOK_URL the same way.

## Where to find more

- [`github-actions.md`](github-actions.md) — prerequisites and manual-dispatch instructions for GitHub Actions.
- [`../.github/workflows/qaff-ci.yml`](../.github/workflows/qaff-ci.yml) — the workflow itself.

## Notifications (opt-in)

Run notifications are enabled for: `githubActions`.
See [`notifications.md`](notifications.md) for the secrets and per-provider behaviour.
