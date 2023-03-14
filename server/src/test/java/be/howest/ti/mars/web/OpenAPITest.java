package be.howest.ti.mars.web;

import be.howest.ti.mars.logic.controller.MockMarsController;
import be.howest.ti.mars.logic.data.Repositories;
import be.howest.ti.mars.web.bridge.MarsOpenApiBridge;
import be.howest.ti.mars.web.bridge.MarsRtcBridge;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(VertxExtension.class)
@SuppressWarnings({"PMD.JUnitTestsShouldIncludeAssert","PMD.AvoidDuplicateLiterals"})
/*
 * PMD.JUnitTestsShouldIncludeAssert: VertxExtension style asserts are marked as false positives.
 * PMD.AvoidDuplicateLiterals: Should all be part of the spec (e.g., urls and names of req/res body properties, ...)
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OpenAPITest {
    private static final Logger LOGGER = Logger.getLogger(OpenAPITest.class.getName());
    private static final int PORT = 8080;
    private static final String HOST = "localhost";
    public static final String MSG_200_EXPECTED = "If all goes right, we expect a 200 status";
    public static final String MSG_201_EXPECTED = "If a resource is successfully created.";
    public static final String MSG_204_EXPECTED = "If a resource is successfully deleted";
    private Vertx vertx;
    private WebClient webClient;

    @BeforeAll
    void deploy(final VertxTestContext testContext) {
        Repositories.shutdown();
        vertx = Vertx.vertx();

        WebServer webServer = new WebServer(new MarsOpenApiBridge(new MockMarsController()), new MarsRtcBridge());
        vertx.deployVerticle(
                webServer,
                testContext.succeedingThenComplete()
        );
        webClient = WebClient.create(vertx);
    }

    @AfterAll
    void close(final VertxTestContext testContext) {
        vertx.close(testContext.succeedingThenComplete());
        webClient.close();
        Repositories.shutdown();
    }

    void request(
            final VertxTestContext testContext,
            HttpRequest<Buffer> request,
            Consumer<HttpResponse<Buffer>> responseCheck
    ) {
        request.send()
                .onFailure(testContext::failNow)
                .onSuccess(response -> testContext.verify(() -> {
                    responseCheck.accept(response);
                    testContext.completeNow();
                }));
    }

    void delete(final VertxTestContext testContext, String endpoint, Consumer<HttpResponse<Buffer>> responseCheck) {
        request(testContext,
                webClient.delete(PORT, HOST, endpoint),
                responseCheck
        );
    }


    void put(final VertxTestContext testContext, Consumer<HttpResponse<Buffer>> responseCheck) {
        request(testContext,
                webClient.put(PORT, HOST, "/api/childcareCenter/1/child/1/disenroll"),
                responseCheck
        );
    }

    void assertErrorResponse(HttpResponse<Buffer> response, int code) {
        LOGGER.log(Level.INFO, String.valueOf(response.statusCode()));
        assertEquals(code, response.statusCode(), response.bodyAsString());
        assertEquals(
                code,
                response.bodyAsJsonObject().getInteger("failure")
        );
        assertNotNull(
                response.bodyAsJsonObject().getString("cause")
        );
    }

    void assertOkResponse(HttpResponse<Buffer> response) {
        assertEquals(200, response.statusCode());
    }

}