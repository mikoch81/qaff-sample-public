package com.example.clientdemopublicqa.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SoftAssertionHelper {

    private final List<AssertionError> failures = new ArrayList<>();

    public void check(String description, Runnable assertion) {
        try {
            assertion.run();
        } catch (AssertionError | RuntimeException e) {
            failures.add(new AssertionError(description + ": " + e.getMessage(), e));
        }
    }

    public void assertAll() {
        if (!failures.isEmpty()) {
            String summary = failures.size() + " assertion(s) failed:\n" +
                    failures.stream().map(Throwable::getMessage).collect(Collectors.joining("\n"));
            AssertionError combined = new AssertionError(summary);
            failures.forEach(combined::addSuppressed);
            failures.clear();
            throw combined;
        }
    }
}
