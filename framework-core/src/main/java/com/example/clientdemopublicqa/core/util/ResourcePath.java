package com.example.clientdemopublicqa.core.util;

import java.net.URL;

/**
 * Resolves classpath resources to {@code file:} URLs usable by Selenium
 * {@code driver.get(...)}. Used by tests to open bundled HTML fixtures
 * without depending on any public website.
 */
public final class ResourcePath {

    private ResourcePath() {
    }

    public static String classpathFileUrl(String classpathName) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        URL url = cl.getResource(classpathName);
        if (url == null) {
            throw new IllegalStateException(
                    "Missing classpath resource: " + classpathName);
        }
        return url.toString();
    }
}
