# How to add an API test

## Overview

API tests use RestAssured to validate HTTP endpoints. This guide shows you how to add a new test.

## Steps

### 1. Create a test class

Add a class to `apps/<id>/api/src/test/java`:

```java
@SpringBootTest
class MyApiTest {
  @Value("${app.base-url}")
  private String baseUrl;

  @Test
  @DisplayName("GET /api/users returns 200")
  void getUsersReturns200() {
    given()
      .baseUri(baseUrl)
      .when()
      .get("/api/users")
      .then()
      .statusCode(200)
      .contentType("application/json");
  }
}
```

### 2. Validate response structure

Use RestAssured assertions to check JSON:

```java
given()
  .baseUri(baseUrl)
  .when()
  .get("/api/users")
  .then()
  .statusCode(200)
  .body("users.size()", greaterThan(0))
  .body("users[0].id", notNullValue());
```

### 3. Test error cases

```java
@Test
void postInvalidUserReturnsBadRequest() {
  given()
    .baseUri(baseUrl)
    .contentType("application/json")
    .body("{}")  // Missing required fields
    .when()
    .post("/api/users")
    .then()
    .statusCode(400);
}
```

### 4. Run locally

```bash
mvn -pl apps/<id>/api test
```

## Example

See `ExampleApiTest.java` in `apps/<id>/api/src/test/java` for a complete
example (generated into the api module of the first app that declares the
`api` layer; if no app declares that layer yet, add it first — see
`qaff add-layer --help`).
