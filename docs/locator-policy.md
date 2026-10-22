# Locator Policy

This policy is static guidance for AI-assisted edits.

## Core rule

Do not invent or hallucinate selectors.

Selectors must be derived from real evidence:

- existing page objects
- local fixture HTML
- real page source supplied by the user
- prior validated selectors in this repository

## Preferred selector order

1. Stable test ids (for example `[data-testid='...']`)
2. Stable semantic attributes
3. Stable IDs
4. XPath/CSS only when justified by evidence

Avoid brittle positional selectors unless justified and documented.

## Future scope

- DOM evidence collection automation is future scope.
- Locator generation is future scope.
- Self-healing locator runtime is future scope.

## Validation

- confirm selectors are represented in evidence
- run `qaff verify --project .`
- run `mvn -B verify`
