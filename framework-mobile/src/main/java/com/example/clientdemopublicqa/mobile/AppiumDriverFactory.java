package com.example.clientdemopublicqa.mobile;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Locale;
import java.util.Properties;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

/**
 * Creates an Appium 2/3 session from {@code appium-config.properties} on the
 * test-resources classpath. Every key resolves from system property (same
 * name) -> environment variable ({@code QAFF_} + upper-snake key, e.g.
 * {@code QAFF_APPIUM_APP}) -> properties file -> default:
 *
 * <pre>
 * appium.serverUrl       = http://localhost:4723
 * appium.platformName    = Android
 * appium.automationName  = UiAutomator2
 * appium.deviceName      = emulator-5554
 * appium.udid            = (optional) adb serial, pins one device
 * appium.app             = path to the APK (relative paths resolve against the working directory)
 * appium.appPackage      = (optional) package of an already-installed app
 * appium.appActivity     = (optional) launch activity of that package
 * appium.newCommandTimeoutSeconds = 120
 * </pre>
 *
 * iOS ({@code appium.platformName=iOS}) reads the same keys with
 * {@code appium.platformVersion} and {@code appium.bundleId} instead of
 * appPackage / appActivity. Android uses typed {@link UiAutomator2Options},
 * iOS typed {@link XCUITestOptions} (Phase 128).
 */
public final class AppiumDriverFactory {

    private AppiumDriverFactory() {}

    public static AppiumDriver create(String propertiesClasspathResource) {
        Properties props = loadProps(propertiesClasspathResource);
        String serverUrl = required(props, "appium.serverUrl");
        String platformName = resolve(props, "appium.platformName", "Android");
        switch (platformName.toLowerCase(Locale.ROOT)) {
            case "android":
                return new AndroidDriver(toUrl(serverUrl), androidOptions(props));
            case "ios":
                return new IOSDriver(toUrl(serverUrl), iosOptions(props));
            default:
                throw new UnsupportedOperationException(
                        "appium.platformName=" + platformName + " is not supported; use Android (UiAutomator2) or iOS (XCUITest).");
        }
    }

    private static UiAutomator2Options androidOptions(Properties props) {
        UiAutomator2Options options = new UiAutomator2Options()
                .setAutomationName(resolve(props, "appium.automationName", "UiAutomator2"))
                .setDeviceName(resolve(props, "appium.deviceName", "emulator-5554"))
                .setNewCommandTimeout(Duration.ofSeconds(
                        Long.parseLong(resolve(props, "appium.newCommandTimeoutSeconds", "120"))));
        String udid = resolve(props, "appium.udid", "");
        if (!udid.isEmpty()) {
            options.setUdid(udid);
        }
        String app = resolve(props, "appium.app", "");
        if (!app.isEmpty()) {
            options.setApp(Path.of(app).toAbsolutePath().normalize().toString());
        }
        String appPackage = resolve(props, "appium.appPackage", "");
        if (!appPackage.isEmpty()) {
            options.setAppPackage(appPackage);
        }
        String appActivity = resolve(props, "appium.appActivity", "");
        if (!appActivity.isEmpty()) {
            options.setAppActivity(appActivity);
        }
        return options;
    }

    private static XCUITestOptions iosOptions(Properties props) {
        XCUITestOptions options = new XCUITestOptions()
                .setAutomationName(resolve(props, "appium.automationName", "XCUITest"))
                .setDeviceName(resolve(props, "appium.deviceName", "iPhone 16"))
                .setNewCommandTimeout(Duration.ofSeconds(
                        Long.parseLong(resolve(props, "appium.newCommandTimeoutSeconds", "120"))));
        String platformVersion = resolve(props, "appium.platformVersion", "");
        if (!platformVersion.isEmpty()) {
            options.setPlatformVersion(platformVersion);
        }
        String udid = resolve(props, "appium.udid", "");
        if (!udid.isEmpty()) {
            options.setUdid(udid);
        }
        String app = resolve(props, "appium.app", "");
        if (!app.isEmpty()) {
            options.setApp(Path.of(app).toAbsolutePath().normalize().toString());
        }
        String bundleId = resolve(props, "appium.bundleId", "");
        if (!bundleId.isEmpty()) {
            options.setBundleId(bundleId);
        }
        return options;
    }

    /** Resolves {@code key}: system property -> {@code QAFF_<KEY>} env var -> file -> fallback. */
    public static String resolve(Properties props, String key, String fallback) {
        String sys = System.getProperty(key);
        if (sys != null && !sys.isBlank()) {
            return sys.trim();
        }
        String env = System.getenv("QAFF_" + key.replaceAll("([a-z])([A-Z])", "$1_$2").replace('.', '_').toUpperCase(Locale.ROOT));
        if (env != null && !env.isBlank()) {
            return env.trim();
        }
        String file = props.getProperty(key);
        if (file != null && !file.isBlank()) {
            return file.trim();
        }
        return fallback;
    }

    public static Properties loadConfig(String propertiesClasspathResource) {
        return loadProps(propertiesClasspathResource);
    }

    private static URL toUrl(String serverUrl) {
        try {
            return URI.create(serverUrl).toURL();
        } catch (IllegalArgumentException | MalformedURLException e) {
            throw new IllegalArgumentException("Invalid Appium server URL: " + serverUrl, e);
        }
    }

    private static Properties loadProps(String resource) {
        Properties props = new Properties();
        try (var stream = AppiumDriverFactory.class.getClassLoader().getResourceAsStream(resource)) {
            if (stream == null) {
                throw new IllegalStateException("Resource not found on classpath: " + resource);
            }
            props.load(stream);
        } catch (java.io.IOException e) {
            throw new IllegalStateException("Failed to load " + resource, e);
        }
        return props;
    }

    private static String required(Properties props, String key) {
        String value = resolve(props, key, "");
        if (value.isEmpty()) {
            throw new IllegalStateException("Required property '" + key + "' is missing or empty");
        }
        return value;
    }
}
