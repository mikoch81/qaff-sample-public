# Scenario Contract

This document defines the static contract used to describe a test scenario for AI-assisted authoring.
It is a documentation and template foundation, not a runtime validator.

## Purpose

- Standardize scenario inputs before any generated test proposal.
- Prevent free-form requests that omit evidence, expected outcomes, or constraints.
- Create deterministic handoff artifacts future agents and future CLI commands can consume.

## Required fields

- `schemaVersion` (number)
- `id` (string)
- `title` (string)
- `appId` (string)
- `layer` (`web-desktop` | `api` | `integration` | `performance`)
- `steps` (array of step objects with stable step ids)
- `expectedResults` (array mapped to step ids)
- `authoringConstraints` (array)
- `unknowns` (array)

Optional fields:

- `source` (human-supplied context, future external mapping)
- `traceability` (human-supplied references)
- `dataRequirements` (fixtures or payload requirements)

## Future mapping notes

- Jira/Zephyr import is future scope.
- If a future import exists, values like issue keys and test case ids are imported/user-supplied data.
- Agents must never invent Jira keys, Zephyr cases, URLs, credentials, or expected results.

## Example

When AI pack is enabled, see `.ai/contracts/scenario-contract.example.json`.
