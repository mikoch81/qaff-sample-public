# Allure Step Mapping Contract

This contract defines how scenario steps map to Allure step output.

## Purpose

- Ensure each meaningful scenario step has a deterministic Allure mapping.
- Keep step names action-oriented and assertion-bound.

## Required fields

- `schemaVersion`
- `scenarioId`
- `mappings` (step-id to Allure step)
- `assertions` (step-id to expected verification)

Optional fields:

- `feature`, `story`, `severity`, `attachments`

## Guardrails

- Do not add hidden steps not represented in the scenario contract.
- Keep expected outcomes explicit and mapped by step id.
- This contract does not introduce test generation runtime.

## Example

When AI pack is enabled, see `.ai/contracts/allure-step-mapping.example.json`.
