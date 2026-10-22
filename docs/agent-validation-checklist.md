# Agent Validation Checklist

Use this checklist after AI-assisted modifications.

## Safety checks

- No invented selectors, URLs, credentials, Jira keys or Zephyr IDs.
- Generated-owned files remain untouched unless explicitly intended.
- Dependency direction remains valid.
- Smoke tests avoid external application targets by default.

## Framework checks

- JUnit 5 and Allure conventions are preserved.
- Page object usage is consistent.
- No `Thread.sleep` unless explicitly justified.
- No brittle positional selectors unless justified by evidence.

## Command checks

- Run `qaff verify --project .`.
- Run `mvn -B verify`.
- Optional targeted scripts under `scripts/`.

`qaff verify` is structural only.

`qaff evaluate-quality-impact` and `qaff update-baseline` require
`framework-performance/reports/<app-id>.result.json` files.

Default `mvn verify` does not run Gatling.

## Scope reminder

This checklist does not imply shipped connectors or runtime integrations.
Jira/Zephyr import, DOM evidence collection, locator generation and
AI test generation runtime are future scope.
