# MCP tool — suggest_test_implementation

## Purpose

Turn the AI authoring contracts in `.ai/contracts/` into a deterministic
implementation plan: where the test belongs, what it should be called,
which steps and page-object methods follow from the evidence, and whether
the inputs are sufficient to start.

## Inputs

Each contract can be passed inline as an object, or as a path under the
project root:

- `scenarioContract` / `scenarioContractRef`
- `testAuthoringRequest` / `testAuthoringRequestRef`
- `locatorEvidence` / `locatorEvidenceRef`
- `allureStepMapping` / `allureStepMappingRef`
- `app`, `layer` — override the target when the contracts disagree.

Nothing is required: with only a free-text `requirement` the tool
answers honestly with `BLOCKED` and lists the missing contracts.

## Outputs

`qaff/mcp-test-implementation-plan/v1`:

- `readiness` — `READY`, `NEEDS_REVIEW` or `BLOCKED`, with reason
  codes, `requiredInputs`, warnings and the commands that validate the
  work.
- `target` — module, base package, test package, class name, file path
  and test method, derived from `qa-factory.lock.json` and the
  framework's package rules; `appInLock` / `layerInLock` say whether the
  target actually exists yet.
- `steps` — scenario steps joined with their Allure step names and the
  expected results that belong to each.
- `pageObjects` — the page-object class to touch, its getter methods and
  the candidate selectors from the evidence contract.
- `guidance` — which agent and prompt in `.ai/` fits this layer.

Steps and page objects are omitted while the verdict is `BLOCKED`.

## Side effects

None. The tool writes no files and emits no Java code — it never invents
selectors, URLs or credentials. Applying the plan remains a human
decision.
