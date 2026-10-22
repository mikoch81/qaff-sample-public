package com.example.clientdemopublicqa.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Reads text resources from the test classpath, decoded as UTF-8. Useful for
 * loading JSON / text payloads and fixtures in API and integration tests.
 *
 * <p>Complements {@link com.example.clientdemopublicqa.core.config.ConfigLoader} (which loads
 * {@code .properties} files) and {@link ResourcePath} (which resolves a resource
 * to a {@code file:} URL). Keep dependency-free so framework-core stays at the
 * bottom of the dependency direction.
 */
public final class Resources {

    private Resources() {
    }

    /** Read a classpath resource fully and return its UTF-8 content. */
    public static String readString(String classpathName) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        try (InputStream in = cl.getResourceAsStream(classpathName)) {
            if (in == null) {
                throw new IllegalStateException(
                        "Missing classpath resource: " + classpathName);
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /** Read a classpath resource and return its UTF-8 lines (terminators stripped). */
    public static List<String> readLines(String classpathName) {
        return List.of(readString(classpathName).split("\\R", -1));
    }
}
