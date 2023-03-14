package be.howest.ti.mars.web;

import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;



class OpenAPIChildcareCenterTests extends OpenAPITest {

    @Test
    void deleteChildFromCenterOk(final VertxTestContext testContext) {
        put(
                testContext,
                this::assertOkResponse
        );
    }

/*
    @Test
    public void deleteChildFromCenterWrongChildId(final VertxTestContext testContext) {
        put(
                testContext,
                "/api/childcareCenter/1/child/99/remove",
                response -> assertErrorResponse(response, 404)
        );
    }

 */
}
