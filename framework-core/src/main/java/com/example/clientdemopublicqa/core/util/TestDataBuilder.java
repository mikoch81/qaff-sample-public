package com.example.clientdemopublicqa.core.util;

/**
 * Generic base class for implementing the Builder pattern in test data objects.
 *
 * <p>Extend this class, set fields in fluent setter methods using {@link #self()},
 * and implement {@link #build()} to construct the target object.
 *
 * <pre>{@code
 * public class UserBuilder extends TestDataBuilder<UserBuilder, User> {
 *     private String name = "Test User";
 *     public UserBuilder withName(String name) { this.name = name; return self(); }
 *     public User build() { return new User(name); }
 * }
 * // Usage:
 * User user = new UserBuilder().withName("Alice").build();
 * }</pre>
 */
public abstract class TestDataBuilder<B extends TestDataBuilder<B, T>, T> {

    @SuppressWarnings("unchecked")
    protected final B self() {
        return (B) this;
    }

    public abstract T build();
}
