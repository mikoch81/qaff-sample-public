package com.example.clientdemopublicqa.apps.demoshop.api.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Feature;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * Example API test demonstrating best practices with RestAssured.
 *
 * This is a template class marked @Disabled. Use it as a reference when
 * writing your own API tests. Do not run it directly.
 *
 * Unlike {@code HealthApiSmokeTest} (offline, in-process HttpServer stub),
 * this template targets a real service. Keep such tests behind
 * {@code @Tag("requires-service")} and load the base URL from
 * {@code api-config.properties} instead of hardcoding it.
 *
 * Best practices demonstrated:
 * - RestAssured fluent API
 * - Clear assertion chains
 * - Status code verification
 * - Response body validation
 * - Allure annotations for reporting
 */
@Disabled("Example template - use as reference, not as a running test")
@Tag("requires-service")
class ExampleApiTest {

  private static final String BASE_URI = "http://localhost:8080";

  @Test
  @DisplayName("GET /api/users returns 200 with valid JSON")
  @Severity(SeverityLevel.CRITICAL)
  @Feature("User API")
  void getUsersReturns200() {
    given()
        .baseUri(BASE_URI)
        .when()
        .get("/api/users")
        .then()
        .statusCode(200)
        .contentType("application/json")
        .body("users.size()", greaterThan(0))
        .body("users[0].id", notNullValue())
        .body("users[0].email", matchesPattern("[\\w\\.]+@[\\w]+\\.[\\w]+"));
  }

  @Test
  @DisplayName("GET /api/users/:id returns 404 for missing user")
  @Severity(SeverityLevel.NORMAL)
  @Feature("User API")
  void getUserByIdReturns404ForMissingUser() {
    given()
        .baseUri(BASE_URI)
        .when()
        .get("/api/users/99999")
        .then()
        .statusCode(404);
  }

  @Test
  @DisplayName("POST /api/users with valid data creates user")
  @Severity(SeverityLevel.CRITICAL)
  @Feature("User API")
  void postUsersCreatesUser() {
    String requestBody = "{"
        + "\"email\": \"newuser@example.com\","
        + "\"firstName\": \"New\","
        + "\"lastName\": \"User\"" + "}";

    given()
        .baseUri(BASE_URI)
        .contentType("application/json")
        .body(requestBody)
        .when()
        .post("/api/users")
        .then()
        .statusCode(201)
        .body("id", notNullValue())
        .body("email", equalTo("newuser@example.com"));
  }

  @Test
  @DisplayName("POST /api/users with missing fields returns 400")
  @Severity(SeverityLevel.NORMAL)
  @Feature("User API")
  void postUsersWithMissingFieldsReturnsBadRequest() {
    String invalidRequest = "{}"; // Missing required fields

    given()
        .baseUri(BASE_URI)
        .contentType("application/json")
        .body(invalidRequest)
        .when()
        .post("/api/users")
        .then()
        .statusCode(400)
        .body("error", notNullValue());
  }

  @Test
  @DisplayName("DELETE /api/users/:id removes user")
  @Severity(SeverityLevel.CRITICAL)
  @Feature("User API")
  void deleteUserRemovesUser() {
    // First create a user (arrange)
    String createResponse = given()
        .baseUri(BASE_URI)
        .contentType("application/json")
        .body("{\"email\": \"delete-test@example.com\"}")
        .when()
        .post("/api/users")
        .then()
        .statusCode(201)
        .extract()
        .path("id")
        .toString();

    // Then delete it (act)
    given()
        .baseUri(BASE_URI)
        .when()
        .delete("/api/users/" + createResponse)
        .then()
        .statusCode(204);

    // Verify it's gone (assert)
    given()
        .baseUri(BASE_URI)
        .when()
        .get("/api/users/" + createResponse)
        .then()
        .statusCode(404);
  }
}
