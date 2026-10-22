# Developer Guide

Welcome to your generated QA framework. This guide points you to the documentation and examples you need to customize the framework for your application.

## Quick Start

1. **Understand what's generated:** Read [GENERATED_FILE_OWNERSHIP.md](GENERATED_FILE_OWNERSHIP.md) to learn which files are managed and which you can safely modify.

2. **Add your first test:** Follow [HOW_TO_ADD_WEB_TEST.md](HOW_TO_ADD_WEB_TEST.md) for step-by-step instructions.

3. **Use testing patterns:** Reference the example classes under `framework-web/src/test/java` and `apps/<id>/web-desktop/src/test/java`.

## Documentation Index

### Customization How-Tos

- [HOW_TO_ADD_WEB_TEST.md](HOW_TO_ADD_WEB_TEST.md) — Add Selenium + JUnit 5 tests
- [HOW_TO_TARGET_YOUR_APP.md](HOW_TO_TARGET_YOUR_APP.md) — Point tests at your own application URL
- [HOW_TO_ADD_PAGE_OBJECT.md](HOW_TO_ADD_PAGE_OBJECT.md) — Create page object classes
- [HOW_TO_ADD_API_TEST.md](HOW_TO_ADD_API_TEST.md) — Add RestAssured API tests
- [HOW_TO_USE_ALLURE.md](HOW_TO_USE_ALLURE.md) — Annotate tests for Allure reports
- [HOW_TO_USE_JUNIT5.md](HOW_TO_USE_JUNIT5.md) — Use JUnit 5 features (tags, parameterization)
- [HOW_TO_RUN_TESTS_LOCALLY.md](HOW_TO_RUN_TESTS_LOCALLY.md) — Run tests on your machine
- [HOW_TO_USE_GENERATED_SCRIPTS.md](HOW_TO_USE_GENERATED_SCRIPTS.md) — Use provided helper scripts
- [ai-web-test-authoring.md](ai-web-test-authoring.md) — AI-assisted web test authoring guardrails
- [ai-api-test-authoring.md](ai-api-test-authoring.md) — AI-assisted API test authoring guardrails
- [locator-policy.md](locator-policy.md) — Selector evidence and anti-hallucination policy
- [allure-step-policy.md](allure-step-policy.md) — Allure step mapping conventions
- [agent-validation-checklist.md](agent-validation-checklist.md) — Final safety checklist after agent edits
- [scenario-contract.md](scenario-contract.md) — Contract for scenario inputs consumed by AI authoring
- [test-authoring-request.md](test-authoring-request.md) — Structured request contract for test authoring tasks
- [locator-evidence-contract.md](locator-evidence-contract.md) — Evidence contract for selector decisions
- [allure-step-mapping-contract.md](allure-step-mapping-contract.md) — Contract for mapping scenario steps to Allure steps
- [generated-test-review-contract.md](generated-test-review-contract.md) — Contract for generated-test review output

### Framework Concepts

- [GENERATED_FILE_OWNERSHIP.md](GENERATED_FILE_OWNERSHIP.md) — Which files are managed and which are safe to edit
- [HOW_TO_INTERPRET_VERIFY.md](HOW_TO_INTERPRET_VERIFY.md) — Understand `qaff verify` output
- [HOW_TO_KEEP_DEPENDENCY_DIRECTION_VALID.md](HOW_TO_KEEP_DEPENDENCY_DIRECTION_VALID.md) — Framework vs. app code rules

### Advanced Topics

- [docs/generated-architecture.md](generated-architecture.md) — Multi-module structure and layer overview
- [ai-authoring-readiness.md](ai-authoring-readiness.md) — Phase 5.2 static AI authoring readiness guidance
- [scenario-contract.md](scenario-contract.md) — Phase 6.0 authoring scenario contract

## Need Help?

- **Example classes** are marked `@Disabled` and located alongside your test code. Use them as templates, not running tests.
- **Local verification:** Run `bash scripts/verify-framework.sh` (or `.ps1` on Windows) to validate your setup.
