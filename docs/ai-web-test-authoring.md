# AI Web Test Authoring

This is static guidance for AI-assisted authoring. It is not a runtime.

## Where to add web tests

- Test classes: `apps/<id>/web-desktop/src/test/java`
- App page objects: `apps/<id>/web-desktop/src/test/java`
- Shared page objects/helpers: `framework-web/src/test/java`
- Fixtures/data: `apps/<id>/web-desktop/src/test/resources`

## Selector policy

- Selectors must come from real app evidence, not hallucination.
- Prefer stable attributes (for example `data-testid`) over positional CSS/XPath.
- If evidence is missing, stop and ask for it. Do not invent selectors.

DOM evidence collection automation is future scope; use available page source,
fixtures and existing page objects as evidence today.

## Conventions

- Keep JUnit 5 structure readable (clear names, deterministic assertions).
- Map test actions to meaningful Allure steps.
- Avoid `Thread.sleep`; use explicit waits.
- Keep smoke tests offline/local where possible.

Generated smoke tests must not call external application targets unless
explicitly configured for that purpose.

## Validate

- `qaff verify --project .`
- `mvn -B verify`
- optional: `scripts/run-web-smoke.*`
