package be.howest.ti.mars.web.bridge;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.validation.RequestParameters;
import io.vertx.ext.web.validation.ValidationHandler;


/**
 * The Request class is responsible for translating information that is part of the
 * request into Java.
 * <p>
 * For every piece of information that you need from the request, you should provide a method here.
 * You can find information in:
 * - the request path: params.pathParameter("some-param-name")
 * - the query-string: params.queryParameter("some-param-name")
 * Both return a `RequestParameter`, which can contain a string or an integer in our case.
 * The actual data can be retrieved using `getInteger()` or `getString()`, respectively.
 * You can check if it is an integer (or not) using `isNumber()`.
 * <p>
 * Finally, some requests have a body. If present, the body will always be in the json format.
 * You can acces this body using: `params.body().getJsonObject()`.
 * <p>
 * **TIP:** Make sure that al your methods have a unique name. For instance, there is a request
 * that consists of more than one "player name". You cannot use the method `getPlayerName()` for both,
 * you will need a second one with a different name.
 */
public class Request {
    public static final String SPEC_CHILD_ID = "childID";
    public static final String SPEC_PARENT_ID = "parentID";
    public static final String SPEC_CENTER_ID = "childcareCenterID";

    public static final String SPEC_CARETAKER_ID = "caretakerID";


    private final RequestParameters params;

    public static Request from(RoutingContext ctx) {
        return new Request(ctx);
    }

    private Request(RoutingContext ctx) {
        this.params = ctx.get(ValidationHandler.REQUEST_CONTEXT_KEY);
    }

    public int getChildId() {
        return params.pathParameter(SPEC_CHILD_ID).getInteger();
    }

    public int getParentId() {
        return params.pathParameter(SPEC_PARENT_ID).getInteger();
    }

    public int getChildcareCenterId() {
        return params.pathParameter(SPEC_CENTER_ID).getInteger();
    }

    public int getCaretakerId() {return params.pathParameter(SPEC_CARETAKER_ID).getInteger();}

}
