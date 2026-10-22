import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

import com.sun.net.httpserver.HttpServer;

/**
 * Minimal local mock for the generated API smoke. Serves GET /health with
 * {"status":"ok"} and answers 404 to everything else. Loopback only.
 *
 * Run: java scripts/ApiMockServer.java [port]   (default 8089)
 */
public final class ApiMockServer {

    public static void main(String[] args) throws IOException {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 8089;
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", port), 0);
        server.createContext("/", exchange -> {
            boolean health = "GET".equals(exchange.getRequestMethod())
                    && "/health".equals(exchange.getRequestURI().getPath());
            byte[] body = (health ? "{\"status\":\"ok\"}" : "{\"error\":\"not found\"}")
                    .getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(health ? 200 : 404, body.length);
            try (var os = exchange.getResponseBody()) {
                os.write(body);
            }
        });
        server.start();
        System.out.println("ApiMockServer listening on http://127.0.0.1:" + port + " (GET /health)");
    }
}
