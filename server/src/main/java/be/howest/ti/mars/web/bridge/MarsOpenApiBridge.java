package be.howest.ti.mars.web.bridge;

import be.howest.ti.mars.logic.controller.DefaultMarsController;
import be.howest.ti.mars.logic.controller.MarsController;
import be.howest.ti.mars.logic.domain.*;
import be.howest.ti.mars.logic.domain.Child;
import be.howest.ti.mars.logic.domain.ChildcareCenter;
import be.howest.ti.mars.logic.domain.Parent;
import be.howest.ti.mars.web.exceptions.MalformedRequestException;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.openapi.RouterBuilder;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * In the MarsOpenApiBridge class you will create one handler-method per API operation.
 * The job of the "bridge" is to bridge between JSON (request and response) and Java (the controller).
 * <p>
 * For each API operation you should get the required data from the `Request` class.
 * The Request class will turn the HTTP request data into the desired Java types (int, String, Custom class,...)
 * This desired type is then passed to the controller.
 * The return value of the controller is turned to Json or another Web data type in the `Response` class.
 */
public class MarsOpenApiBridge {
    private static final Logger LOGGER = Logger.getLogger(MarsOpenApiBridge.class.getName());
    private final MarsController controller;

    public Router buildRouter(RouterBuilder routerBuilder) {
        LOGGER.log(Level.INFO, "Installing cors handlers");
        routerBuilder.rootHandler(createCorsHandler());

        LOGGER.log(Level.INFO, "Installing failure handlers for all operations");
        routerBuilder.operations().forEach(op -> op.failureHandler(this::onFailedRequest));

        LOGGER.log(Level.INFO, "Installing handler for: getAllChildren");
        routerBuilder.operation("getAllChildren").handler(this::getAllChildren);

        LOGGER.log(Level.INFO, "Installing handler for: getAllCenters");
        routerBuilder.operation("getAllChildcareCenters").handler(this::getAllChildcareCenters);

        LOGGER.log(Level.INFO, "Installing handler for: getCaretakerFromChildcareCenter");
        routerBuilder.operation("getCaretaker").handler(this::getCaretakerFromChildcareCenter);

        LOGGER.log(Level.INFO, "Installing handler for: getChild");
        routerBuilder.operation("getChild").handler(this::getChild);

        LOGGER.log(Level.INFO, "Installing handler for: removeChildfromChildcareCenter");
        routerBuilder.operation("disenrollChildFromChildcareCenter").handler(this::removeChildFromChildcareCenter);

        LOGGER.log(Level.INFO, "Installing handler for: addChildToChildcareCenter");
        routerBuilder.operation("enrollChildIntoChildcareCenter").handler(this::addChildToChildcareCenter);

        LOGGER.log(Level.INFO, "Installing handler for: getParent");
        routerBuilder.operation("getParent").handler(this::getParent);

        LOGGER.log(Level.INFO, "Installing handler for: getChildcareCenter");
        routerBuilder.operation("getChildcareCenter").handler(this::getChildcareCenter);

        LOGGER.log(Level.INFO, "Installing handler for: getAllChildrenFromCenter");
        routerBuilder.operation("getChildList").handler(this::getAllChildrenFromChildcareCenter);

        LOGGER.log(Level.INFO, "Installing handler for: getAllCaretakersFromCenter");
        routerBuilder.operation("getCaretakerList").handler(this::getAllCaretakersFromChildcareCenter);

        LOGGER.log(Level.INFO, "Installing handler for: history");
        routerBuilder.operation("getChildHistory").handler(this::getHistory);

        LOGGER.log(Level.INFO, "Installing handler for: getAllChildrenFromParent");
        routerBuilder.operation("getParentsChildren").handler(this::getAllChildrenFromParent);

        LOGGER.log(Level.INFO, "Installing handler for: eventTypes");
        routerBuilder.operation("getEventTypes").handler(this::getEventTypes);

        LOGGER.log(Level.INFO, "Installing handler for: getMenus");
        routerBuilder.operation("getMenus").handler(this::getMenus);

        LOGGER.log(Level.INFO, "Installing handler for: parentList");
        routerBuilder.operation("getParentList").handler(this::getParentList);

        LOGGER.log(Level.INFO, "All handlers are installed, creating router.");
        return routerBuilder.createRouter();
    }

    public MarsOpenApiBridge() {
        this.controller = new DefaultMarsController();
    }

    public MarsOpenApiBridge(MarsController controller) {
        this.controller = controller;
    }

    private void getChild(RoutingContext routingContext) {
        Child child = controller.getChild(Request.from(routingContext).getChildId());
        Response.sendChild(routingContext,child);
    }

    private void addChildToChildcareCenter(RoutingContext routingContext) {
        Request request = Request.from(routingContext);
        controller.addChildToChildcareCenter(request.getChildId(), request.getChildcareCenterId());
        Child child = controller.getChild(Request.from(routingContext).getChildId());
        Response.sendChild(routingContext, child);
    }

    private void getParentList(RoutingContext routingContext){
        Request request = Request.from(routingContext);
        List<Parent> parents = controller.getParentsFromChildCareCenter(request.getChildcareCenterId());
        Response.sendJsonListResponse(routingContext,parents);
    }

    private void removeChildFromChildcareCenter(RoutingContext routingContext) {
        Request request = Request.from(routingContext);
        controller.removeChildFromChildcareCenter(request.getChildId(), request.getChildcareCenterId());
        Child child = controller.getChild(Request.from(routingContext).getChildId());
        Response.sendChild(routingContext, child);
    }

    public void getAllChildren(RoutingContext ctx){
        List<Child> children = controller.getAllChildren();
        Response.sendCustomIdResponse(ctx,children);
    }

    public void getEventTypes(RoutingContext ctx) {
        List<String> eventList = controller.getEventTypes();
        Response.sendJsonListResponse(ctx, eventList);
    }

    public void getHistory(RoutingContext ctx){
        List<Event> eventsFromToday= controller.getEventsFromChildAndCurrentDay(Request.from(ctx).getChildId());
        Response.sendJsonListResponse(ctx,eventsFromToday);
    }

    public void getParent(RoutingContext ctx) {
        Parent parent = controller.getParent(Request.from(ctx).getParentId());
        Response.sendParent(ctx, parent);
    }
    private void getChildcareCenter(RoutingContext ctx) {
        ChildcareCenter childcareCenter = controller.getChildcareCenter(Request.from(ctx).getChildcareCenterId());
        Response.sendChildcareCenter(ctx, childcareCenter);
    }

    public void getAllChildcareCenters(RoutingContext ctx){
        List<ChildcareCenter> centers = controller.getAllChildcareCenters();
        Response.sendJsonListResponse(ctx, centers);
    }

    public void getAllChildrenFromChildcareCenter(RoutingContext ctx){
        List<Child> children = controller.getAllChildrenFromChildcareCenter(Request.from(ctx).getChildcareCenterId());
        Response.sendJsonListResponse(ctx, children);
    }
    public void getAllCaretakersFromChildcareCenter(RoutingContext ctx){
        List<Caretaker> caretakers = controller.getAllCaretakersFromChildcareCenter(Request.from(ctx).getChildcareCenterId());
        Response.sendJsonListResponse(ctx, caretakers);
    }

    public void getCaretakerFromChildcareCenter(RoutingContext ctx){
        Caretaker caretaker = controller.getCaretakerFromChildcareCenter(Request.from(ctx).getChildcareCenterId(), Request.from(ctx).getCaretakerId());
        Response.sendJsonListResponse(ctx, caretaker);
    }


    public void getAllChildrenFromParent(RoutingContext ctx){
        List<Child> children = controller.getParent(Request.from(ctx).getParentId()).getChildren();
        Response.sendJsonListResponse(ctx, children);
    }

    public void getMenus(RoutingContext ctx){
        Response.sendMenus(ctx, 200);
    }

    private void onFailedRequest(RoutingContext ctx) {
        Throwable cause = ctx.failure();
        int code = ctx.statusCode();

        String quote = Objects.isNull(cause) ? "" + code : cause.getMessage();

        // Map custom runtime exceptions to a HTTP status code.
        LOGGER.log(Level.INFO, "Error-code: {0}",code);
        LOGGER.log(Level.INFO, "Failed request", cause);
        if (cause instanceof IllegalArgumentException) {
            code = 400;
        } else if (cause instanceof MalformedRequestException) {
            code = 400;
        } else if (cause instanceof NoSuchElementException) {
            code = 404;
        } else if (cause instanceof IllegalStateException) {
            code = 409;
        }
        else {
            LOGGER.log(Level.WARNING, "Failed request", cause);
        }

        Response.sendFailure(ctx, code, quote);
    }

    private CorsHandler createCorsHandler() {
        return CorsHandler.create(".*.")
                .allowedHeader("x-requested-with")
                .allowedHeader("Access-Control-Allow-Origin")
                .allowedHeader("Access-Control-Allow-Credentials")
                .allowCredentials(true)
                .allowedHeader("origin")
                .allowedHeader("Content-Type")
                .allowedHeader("Authorization")
                .allowedHeader("accept")
                .allowedMethod(HttpMethod.HEAD)
                .allowedMethod(HttpMethod.GET)
                .allowedMethod(HttpMethod.POST)
                .allowedMethod(HttpMethod.OPTIONS)
                .allowedMethod(HttpMethod.PATCH)
                .allowedMethod(HttpMethod.DELETE)
                .allowedMethod(HttpMethod.PUT);
    }
}
