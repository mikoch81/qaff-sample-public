# Generated Test Review Contract

This contract defines deterministic review output for AI-assisted generated tests.

## Purpose

- Standardize findings across generated test reviews.
- Keep review outcomes machine-readable for future automation.

## Required fields

- `schemaVersion`
- `reviewId`
- `inputs`
- `findings`
- `verdict`
- `requiredCommands`

Recommended verdict values:

- `approved`
- `changes_requested`
- `blocked_missing_evidence`

## Guardrails

- Missing evidence should block approval.
- Findings must cite concrete contract or evidence gaps.
- Do not invent successful command output.

## Example

When AI pack is enabled, see `.ai/contracts/generated-test-review.example.json`.
