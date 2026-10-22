# Generation Summary

`qaff create` produced a real, buildable Java/Maven/Selenium/JUnit5/Allure
framework from the inputs below.

## Inputs

- **Config:** `client-demo-public.yaml`
- **Project:** `client-demo-public-qa`
- **Java target:** `21`
- **Blueprint:** `java-maven-selenium-junit5-allure` v0.1.0

## Selected blueprint

- **id:** `java-maven-selenium-junit5-allure`
- **version:** 0.1.0
- **name:** Java + Maven + Selenium 4 + JUnit 5 + Allure
- **maturity:** stable
- **description:** Default web-automation blueprint. Generates a Maven multi-module Java framework with Selenium 4, JUnit 5 and Allure 2 reporting. Targets Java 21 (default) with Java 17 as a compatibility option.
- **language:** java
- **buildTool:** maven
- **testRunner:** junit5
- **automationTools:** selenium-4, webdrivermanager
- **reportingTools:** allure-2
- **supportedChannels:** web-desktop
- **supportedIntegrations:** github-actions, jenkins
- **supportedQualityPacks:** integration, api, performance
- **outputStructure:** pom.xml, framework-core/, framework-web/, apps/, qa-factory.lock.json
- **manifest path:** `java-maven-selenium-junit5-allure/blueprint.yaml`

## Applications

- `apps/demo-shop` — Demo Shop (Swag Labs)  (layers: web-desktop, api)

- **External web targets:** `demo-shop` → `https://www.saucedemo.com/` — the web smoke runs against these URLs, so the first `mvn verify` fails until they are reachable (replace a placeholder such as `https://example.invalid`; `qaff doctor --project .` probes them; see README "External web targets").

## Files written

```
.ai/agents/performance-analyst.agent.md
.ai/mcp/mcp-config.example.json
.ai/mcp/performance-results-mcp.md
.ai/mcp/README.md
.ai/mcp/repository-inspection-mcp.md
.ai/mcp/server.manifest.json
.ai/mcp/server/server.js
.ai/mcp/test-results-mcp.md
.ai/mcp/tools/attach-report-to-jira.tool.md
.ai/mcp/tools/compare-performance-baseline.tool.md
.ai/mcp/tools/create-zephyr-execution.tool.md
.ai/mcp/tools/inspect-repository-structure.tool.md
.ai/mcp/tools/read-test-results.tool.md
.ai/mcp/tools/suggest-test-implementation.tool.md
.ai/mcp/tools/sync-testrail-results.tool.md
.ai/mcp/tools/sync-xray-results.tool.md
.github/workflows/qaff-ci.yml
.gitignore
apps/demo-shop/api/pom.xml
apps/demo-shop/api/src/test/java/com/example/clientdemopublicqa/apps/demoshop/api/api/ExampleApiTest.java
apps/demo-shop/api/src/test/java/com/example/clientdemopublicqa/apps/demoshop/api/api/HealthApiSmokeTest.java
apps/demo-shop/api/src/test/resources/api-config.properties
apps/demo-shop/web-desktop/pom.xml
apps/demo-shop/web-desktop/src/test/java/com/example/clientdemopublicqa/apps/demoshop/webdesktop/ExampleTestData.java
apps/demo-shop/web-desktop/src/test/java/com/example/clientdemopublicqa/apps/demoshop/webdesktop/ExampleWebTest.java
apps/demo-shop/web-desktop/src/test/java/com/example/clientdemopublicqa/apps/demoshop/webdesktop/pages/DemoHomePage.java
apps/demo-shop/web-desktop/src/test/java/com/example/clientdemopublicqa/apps/demoshop/webdesktop/pages/ProductCardComponent.java
apps/demo-shop/web-desktop/src/test/java/com/example/clientdemopublicqa/apps/demoshop/webdesktop/tests/DemoHomeSmokeTest.java
apps/demo-shop/web-desktop/src/test/java/com/example/clientdemopublicqa/apps/demoshop/webdesktop/tests/DemoProductCardTest.java
apps/demo-shop/web-desktop/src/test/resources/allure.properties
apps/demo-shop/web-desktop/src/test/resources/config.properties
apps/demo-shop/web-desktop/src/test/resources/fixtures/demo-shop.html
config/zephyr-mapping.json
docs/agent-validation-checklist.md
docs/ai-api-test-authoring.md
docs/ai-authoring-readiness.md
docs/ai-web-test-authoring.md
docs/allure-step-mapping-contract.md
docs/allure-step-policy.md
docs/ci-cd.md
docs/DEVELOPER_GUIDE.md
docs/GENERATED_FILE_OWNERSHIP.md
docs/generated-architecture.md
docs/generated-test-review-contract.md
docs/github-actions.md
docs/HOW_TO_ADD_API_TEST.md
docs/HOW_TO_ADD_PAGE_OBJECT.md
docs/HOW_TO_ADD_WEB_TEST.md
docs/HOW_TO_INTERPRET_VERIFY.md
docs/HOW_TO_KEEP_DEPENDENCY_DIRECTION_VALID.md
docs/HOW_TO_RUN_TESTS_LOCALLY.md
docs/HOW_TO_TARGET_YOUR_APP.md
docs/HOW_TO_UPDATE_BASELINE.md
docs/HOW_TO_USE_ALLURE.md
docs/HOW_TO_USE_GENERATED_SCRIPTS.md
docs/HOW_TO_USE_JUNIT5.md
docs/HOW_TO_USE_QUALITY_IMPACT.md
docs/how-to-run.md
docs/local-fixture.md
docs/local-scripts.md
docs/locator-evidence-contract.md
docs/locator-policy.md
docs/notifications.md
docs/performance-strategy.md
docs/quality-gates.md
docs/scenario-contract.md
docs/test-authoring-request.md
framework-api/pom.xml
framework-api/README.md
framework-api/src/main/java/com/example/clientdemopublicqa/api/ApiConfig.java
framework-api/src/main/java/com/example/clientdemopublicqa/api/ApiTestSupport.java
framework-core/pom.xml
framework-core/src/main/java/com/example/clientdemopublicqa/core/config/ConfigLoader.java
framework-core/src/main/java/com/example/clientdemopublicqa/core/util/ResourcePath.java
framework-core/src/main/java/com/example/clientdemopublicqa/core/util/Resources.java
framework-core/src/main/java/com/example/clientdemopublicqa/core/util/Retry.java
framework-core/src/main/java/com/example/clientdemopublicqa/core/util/SoftAssertionHelper.java
framework-core/src/main/java/com/example/clientdemopublicqa/core/util/TestDataBuilder.java
framework-performance/baseline/README.md
framework-performance/pom.xml
framework-performance/providers/gatling-java/README.md
framework-performance/providers/jmeter/input/README.md
framework-performance/providers/jmeter/README.md
framework-performance/providers/jmh/README.md
framework-performance/providers/k6/input/README.md
framework-performance/providers/k6/README.md
framework-performance/README.md
framework-performance/reports/history/README.md
framework-performance/reports/README.md
framework-performance/thresholds.yaml
framework-web-playwright/pom.xml
framework-web-playwright/src/main/java/com/example/clientdemopublicqa/webplaywright/config/SmokeExpectations.java
framework-web-playwright/src/main/java/com/example/clientdemopublicqa/webplaywright/config/WebConfig.java
framework-web-playwright/src/main/java/com/example/clientdemopublicqa/webplaywright/driver/PlaywrightFactory.java
framework-web-playwright/src/main/java/com/example/clientdemopublicqa/webplaywright/driver/PlaywrightSession.java
framework-web-playwright/src/main/java/com/example/clientdemopublicqa/webplaywright/extension/RegressionTest.java
framework-web-playwright/src/main/java/com/example/clientdemopublicqa/webplaywright/extension/RetryExtension.java
framework-web-playwright/src/main/java/com/example/clientdemopublicqa/webplaywright/extension/RetryingTest.java
framework-web-playwright/src/main/java/com/example/clientdemopublicqa/webplaywright/extension/ScreenshotOnFailureExtension.java
framework-web-playwright/src/main/java/com/example/clientdemopublicqa/webplaywright/extension/SmokeTest.java
framework-web-playwright/src/main/java/com/example/clientdemopublicqa/webplaywright/page/BasePage.java
framework-web-playwright/src/main/java/com/example/clientdemopublicqa/webplaywright/page/PageComponent.java
framework-web-playwright/src/main/java/com/example/clientdemopublicqa/webplaywright/test/BaseWebMobileTest.java
framework-web-playwright/src/main/java/com/example/clientdemopublicqa/webplaywright/test/BaseWebTest.java
pom.xml
qa-factory.lock.json
README.md
scripts/allure-report.ps1
scripts/allure-report.sh
scripts/ApiMockServer.java
scripts/run-all-tests.ps1
scripts/run-all-tests.sh
scripts/run-android-smoke.ps1
scripts/run-android-smoke.sh
scripts/run-api-mock.ps1
scripts/run-api-mock.sh
scripts/run-api-smoke.ps1
scripts/run-api-smoke.sh
scripts/run-gatling-smoke.ps1
scripts/run-gatling-smoke.sh
scripts/run-integration-smoke.ps1
scripts/run-integration-smoke.sh
scripts/run-ios-smoke.ps1
scripts/run-ios-smoke.sh
scripts/run-jmeter-import-smoke.ps1
scripts/run-jmeter-import-smoke.sh
scripts/run-k6-import-smoke.ps1
scripts/run-k6-import-smoke.sh
scripts/run-mobile-appium-smoke.ps1
scripts/run-mobile-appium-smoke.sh
scripts/run-performance-smoke.ps1
scripts/run-performance-smoke.sh
scripts/run-web-external-smoke.ps1
scripts/run-web-external-smoke.sh
scripts/run-web-mobile-smoke.ps1
scripts/run-web-mobile-smoke.sh
scripts/run-web-smoke.ps1
scripts/run-web-smoke.sh
scripts/verify-framework.ps1
scripts/verify-framework.sh
GENERATION_SUMMARY.md
```

## Post-generation validation

- **Dependency direction:** PASSED
- **Modules checked:** 6
- **Rule:** `apps/* -> framework-*` allowed; `framework-* -> apps/*` forbidden.

## Build it

```bash
mvn verify
```
