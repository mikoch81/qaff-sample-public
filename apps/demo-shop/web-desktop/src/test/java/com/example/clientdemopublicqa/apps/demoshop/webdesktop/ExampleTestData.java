package com.example.clientdemopublicqa.apps.demoshop.webdesktop.test.data;

/**
 * Example test data builder demonstrating best practices.
 * 
 * This is a template class marked as an example. Use it as a reference when
 * building test data fixtures.
 * 
 * Best practices demonstrated:
 * - Builder pattern for flexible test data construction
 * - Fluent interface (method chaining)
 * - Sensible defaults
 * - Clear, domain-specific method names
 * - Factory methods for common scenarios
 */
public class ExampleTestData {

  // Example: User builder
  public static class UserBuilder {
    private String email = "testuser@example.com";
    private String firstName = "Test";
    private String lastName = "User";
    private String password = "SecurePassword123!";
    private boolean active = true;

    public UserBuilder() {
    }

    public UserBuilder withEmail(String email) {
      this.email = email;
      return this;
    }

    public UserBuilder withFirstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    public UserBuilder withLastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    public UserBuilder withPassword(String password) {
      this.password = password;
      return this;
    }

    public UserBuilder inactive() {
      this.active = false;
      return this;
    }

    public String build() {
      // In real code, return a constructed object or JSON representation
      return "{"
          + "\"email\": \"" + email + "\", "
          + "\"firstName\": \"" + firstName + "\", "
          + "\"lastName\": \"" + lastName + "\", "
          + "\"active\": " + active
          + "}";
    }
  }

  // Factory methods for common scenarios
  public static UserBuilder aDefaultUser() {
    return new UserBuilder();
  }

  public static UserBuilder anAdminUser() {
    return new UserBuilder().withEmail("admin@example.com").withFirstName("Admin");
  }

  public static UserBuilder aGuestUser() {
    return new UserBuilder().withEmail("guest@example.com").inactive();
  }

  /**
   * Example usage:
   * 
   * String testUser = aDefaultUser()
   *   .withEmail("custom@example.com")
   *   .withFirstName("Custom")
   *   .build();
   * 
   * String adminUser = anAdminUser().build();
   */
}
