# Test Authoring Request Contract

This contract defines the structured request consumed by AI-assisted authoring prompts.
It is static guidance and templates only.

## Purpose

- Tie a request to a Scenario Contract.
- Require evidence references before code suggestions.
- Record allowed files and forbidden changes.

## Required fields

- `schemaVersion`
- `requestId`
- `scenarioContractRef`
- `target` (app + layer)
- `requiredEvidenceRefs`
- `allowedFiles`
- `forbiddenChanges`
- `validationCommands`
- `reviewContractRef`

## Guardrails

- Missing evidence should block authoring output.
- Do not invent selectors, test data, URLs, credentials, expected outcomes, or external IDs.
- Do not suggest edits to generated-owned files unless explicitly requested.

## Example

When AI pack is enabled, see `.ai/contracts/test-authoring-request.template.json`.
