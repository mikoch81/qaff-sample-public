# Locator Evidence Contract

This contract defines the evidence required to justify selectors.

## Purpose

- Record where selector evidence came from.
- Prefer stable attributes over brittle positional selectors.
- Make missing evidence explicit instead of guessed.

## Required fields

- `schemaVersion`
- `evidenceId`
- `appId`
- `pageOrComponent`
- `source`
- `elements`
- `prohibitedSelectors`
- `gaps`

## Future mapping notes

- DOM/page-source collection automation is future scope.
- Locator generation and self-healing runtime are future scope.
- In this phase, evidence is manually supplied or fixture-based.

## Guardrails

- Never invent selectors.
- Never mark unsupported selectors as validated.
- Missing evidence must be kept as a gap for human resolution.

## Example

When AI pack is enabled, see `.ai/contracts/locator-evidence.example.json`.
