# Allure Step Policy

This policy defines static conventions for mapping test actions to Allure steps.

## Step mapping

- Map each meaningful user/system action to one clear step.
- Keep step names action-oriented and short.
- Keep assertions explicit and close to their related step.

## JUnit 5 and Allure conventions

- Use `@DisplayName` for readable test intent.
- Use Allure annotations consistently (for example `@Feature`, `@Story`, `@Severity`).
- Keep tests deterministic and avoid hidden side effects.

## Scope note

This guidance does not introduce a runtime generator.
AI test generation runtime remains future scope.

## Validation

- `qaff verify --project .`
- `mvn -B verify`
