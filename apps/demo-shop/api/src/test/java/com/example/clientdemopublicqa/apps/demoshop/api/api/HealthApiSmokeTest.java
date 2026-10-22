package com.example.clientdemopublicqa.apps.demoshop.api.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

import com.sun.net.httpserver.HttpServer;

import com.example.clientdemopublicqa.api.ApiConfig;
import com.example.clientdemopublicqa.api.ApiTestSupport;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Minimal RestAssured smoke for the API layer, driven by
 * {@code api-config.properties} ({@link ApiConfig}). With the default
 * {@code api.baseUrl=local} it spins up an in-process {@link HttpServer} on a
 * random port so the test is fully offline; with a real base URL it calls
 * {@code api.healthPath} on your service instead — no code change needed.
 */
@Epic("demo-shop")
@Feature("API")
public class HealthApiSmokeTest {

    private ApiConfig cfg;
    private HttpServer server;
    private String baseUrl;

    @BeforeEach
    void resolveTarget() throws Exception {
        cfg = ApiConfig.load("api-config.properties");
        if (!cfg.isLocal()) {
            baseUrl = cfg.baseUrl();
            return;
        }
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext(cfg.healthPath(), exchange -> {
            byte[] body = "{\"status\":\"ok\",\"app\":\"demo-shop\"}".getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, body.length);
            try (var os = exchange.getResponseBody()) {
                os.write(body);
            }
        });
        server.start();
        baseUrl = "http://127.0.0.1:" + server.getAddress().getPort();
    }

    @AfterEach
    void stopStub() {
        if (server != null) {
            server.stop(0);
        }
    }

    @Test
    @Story("Smoke")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("GET health endpoint returns 200")
    void healthEndpointResponds() {
        Response r = ApiTestSupport.baseSpec(baseUrl)
                .when().get(cfg.healthPath());

        assertEquals(200, r.statusCode(), "expected HTTP 200 from " + baseUrl + cfg.healthPath());
        if (cfg.isLocal()) {
            assertEquals("ok", r.jsonPath().getString("status"), "expected status=ok");
            assertEquals("demo-shop", r.jsonPath().getString("app"), "expected app=demo-shop");
        }
    }
}
