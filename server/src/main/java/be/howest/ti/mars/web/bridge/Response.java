package be.howest.ti.mars.web.bridge;

import be.howest.ti.mars.logic.domain.*;
import be.howest.ti.mars.logic.exceptions.RepositoryException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.parsetools.JsonParser;
import io.vertx.ext.web.RoutingContext;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

/**
 * The Response class is responsible for translating the result of the controller into
 * JSON responses with an appropriate HTTP code.
 */
public class Response {

    public static final String APPLICATION_JSON = "application/json";

    private Response() { }

    public static void sendCustomIdResponse(RoutingContext ctx, List<Child> childList){
        //generated custom response, as this was not possible using JsonIgnore tags without damaging other responses.
        JsonArray model = new JsonArray();
        for (Child child: childList){
            JsonObject object = new JsonObject();
            object.put("ChildID",child.getChildID());
            object.put("name",child.getChildName());
            model.add(object);
        }
        sendJsonListResponse(ctx,model);
    }

    public static void sendJsonListResponse(RoutingContext ctx, Object response) {
        ctx.response()
                .putHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON)
                .end(Json.encodePrettily(response));
    }

    public static void sendChild(RoutingContext ctx, Child child) {
        sendOkJsonResponse(ctx, JsonObject.mapFrom(child));
    }

    public static void sendParent(RoutingContext ctx, Parent parent) {
        sendOkJsonResponse(ctx, JsonObject.mapFrom(parent));
    }

    public static void sendChildcareCenter(RoutingContext ctx, ChildcareCenter childcareCenter) {
        sendOkJsonResponse(ctx, JsonObject.mapFrom(childcareCenter));
    }

    private static void sendOkJsonResponse(RoutingContext ctx, JsonObject response) {
        sendJsonResponse(ctx, 200, response);
    }

    public static void sendEmptyResponse(RoutingContext ctx, int statusCode) {
        ctx.response()
                .setStatusCode(statusCode)
                .end();
    }

    private static void sendJsonResponse(RoutingContext ctx, int statusCode, Object response) {
        ctx.response()
                .putHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON)
                .setStatusCode(statusCode)
                .end(Json.encodePrettily(response));
    }

    public static void sendFailure(RoutingContext ctx, int code, String quote) {
        sendJsonResponse(ctx, code, new JsonObject()
                .put("failure", code)
                .put("cause", quote));
    }

    public static void sendMenus(RoutingContext ctx, int statusCode){
        JsonArray response = new JsonArray();

        JsonObject week1 = new JsonObject()
                .put("dateStartWeek", "23-01-2023")
                .put("dateEndWeek", "29-01-2023")
                .put("monday", new JsonObject()
                        .put("breakfast", new JsonObject().put("drinks", "Chocolate milk").put("meal", "Croissants"))
                        .put("lunch", new JsonObject().put("drinks", "Water").put("meal", "Grilled cheese sandwich with a side of carrot sticks and cherry tomatoes"))
                        .put("dinner", new JsonObject().put("drinks", "Water").put("meal", "Baked chicken breasts with roasted vegetables and a small side salad")))
                .put("tuesday", new JsonObject()
                        .put("breakfast", new JsonObject().put("drinks", "Milk").put("meal", "Sandwiches"))
                        .put("lunch", new JsonObject().put("drinks", "Water").put("meal", "Turkey and cheese roll-ups with a side of sliced cucumbers and hummus"))
                        .put("dinner", new JsonObject().put("drinks", "Water").put("meal", "Spaghetti with a side of steamed broccoli and garlic bread")))
                .put("wednesday", new JsonObject()
                        .put("breakfast", new JsonObject().put("drinks", "Milk").put("meal", "Cereals"))
                        .put("lunch", new JsonObject().put("drinks", "Water").put("meal", "Peanut butter and jelly sandwich with a side of sliced apples and a cheese stick"))
                        .put("dinner", new JsonObject().put("drinks", "Water").put("meal", "Grilled salmon with quinoa and roasted asparagus")))
                .put("thursday", new JsonObject()
                        .put("breakfast", new JsonObject().put("drinks", "Milk").put("meal", "Sandwiches"))
                        .put("lunch", new JsonObject().put("drinks", "Water").put("meal", "Hummus and veggie wrap with a side of cherry tomatoes and pretzels"))
                        .put("dinner", new JsonObject().put("drinks", "Water").put("meal", "Slow cooker beef and vegetable stew with a small side of whole grain rolls")))
                .put("friday", new JsonObject()
                        .put("breakfast", new JsonObject().put("drinks", "Milk").put("meal", "Croissants"))
                        .put("lunch", new JsonObject().put("drinks", "Water").put("meal", "Turkey and cheese quesadilla with a side of black bean salad"))
                        .put("dinner", new JsonObject().put("drinks", "Water").put("meal", "Baked pork chops with mashed sweet potatoes and steamed green beans")))
                .put("saturday", new JsonObject()
                        .put("breakfast", new JsonObject().put("drinks", "Milk").put("meal", "Sandwiches"))
                        .put("lunch", new JsonObject().put("drinks", "Water").put("meal", "Grilled chicken Caesar salad"))
                        .put("dinner", new JsonObject().put("drinks", "Water").put("meal", "Black bean and corn tacos with a side of Spanish rice")))
                .put("sunday", new JsonObject()
                        .put("breakfast", new JsonObject().put("drinks", "Chocolate milk").put("meal", "Croissants"))
                        .put("lunch", new JsonObject().put("drinks", "Water").put("meal", "Peanut butter and jelly sandwiches with a side of sliced carrots and ranch dressing"))
                        .put("dinner", new JsonObject().put("drinks", "Water").put("meal", "Baked ziti with a small side salad and garlic bread")));

        JsonObject week2 = new JsonObject()
                .put("dateStartWeek", "30-01-2023")
                .put("dateEndWeek", "05-02-2023")
                .put("monday", new JsonObject()
                        .put("breakfast", new JsonObject().put("drinks", "Chocolate milk").put("meal", "Croissants"))
                        .put("lunch", new JsonObject().put("drinks", "Water").put("meal", "Tuna salad sandwiches with carrot sticks and a piece of fruit"))
                        .put("dinner", new JsonObject().put("drinks", "Water").put("meal", "Chili with whole grain cornbread and a side of fruit.")))
                .put("tuesday", new JsonObject()
                        .put("breakfast", new JsonObject().put("drinks", "Milk").put("meal", "Sandwiches"))
                        .put("lunch", new JsonObject().put("drinks", "Water").put("meal", "Whole grain pita pockets with turkey, cheese, and lettuce"))
                        .put("dinner", new JsonObject().put("drinks", "Water").put("meal", "Grilled vegetable skewers with quinoa and hummus")))
                .put("wednesday", new JsonObject()
                        .put("breakfast", new JsonObject().put("drinks", "Milk").put("meal", "Cereals"))
                        .put("lunch", new JsonObject().put("drinks", "Water").put("meal", "Grilled chicken salad with mixed greens and avocado"))
                        .put("dinner", new JsonObject().put("drinks", "Water").put("meal", "Baked tofu with brown rice and steamed broccoli")))
                .put("thursday", new JsonObject()
                        .put("breakfast", new JsonObject().put("drinks", "Milk").put("meal", "Sandwiches"))
                        .put("lunch", new JsonObject().put("drinks", "Water").put("meal", "Whole grain crackers with hummus and cherry tomatoes"))
                        .put("dinner", new JsonObject().put("drinks", "Water").put("meal", "Slow cooker beef stew with whole grain bread")))
                .put("friday", new JsonObject()
                        .put("breakfast", new JsonObject().put("drinks", "Milk").put("meal", "Croissants"))
                        .put("lunch", new JsonObject().put("drinks", "Water").put("meal", "Turkey and cheese quesadilla with a side of black bean salad"))
                        .put("dinner", new JsonObject().put("drinks", "Water").put("meal", "Baked pork chops with mashed sweet potatoes and steamed green beans")))
                .put("saturday", new JsonObject()
                        .put("breakfast", new JsonObject().put("drinks", "Milk").put("meal", "Sandwiches"))
                        .put("lunch", new JsonObject().put("drinks", "Water").put("meal", "Turkey and cheese roll-ups with cucumber slices and ranch dressing"))
                        .put("dinner", new JsonObject().put("drinks", "Water").put("meal", "Baked salmon with quinoa and roasted asparagus")))
                .put("sunday", new JsonObject()
                        .put("breakfast", new JsonObject().put("drinks", "Chocolate milk").put("meal", "Croissants"))
                        .put("lunch", new JsonObject().put("drinks", "Water").put("meal", "Peanut butter and jelly sandwich with apple slices and a piece of string cheese"))
                        .put("dinner", new JsonObject().put("drinks", "Water").put("meal", "Spaghetti with homemade marinara sauce and a side salad")));

        JsonObject week3 = new JsonObject()
                .put("dateStartWeek", "06-02-2023")
                .put("dateEndWeek", "12-02-2023")
                .put("description", "To be announced!");


        response.add(week1);
        response.add(week2);
        response.add(week3);

        sendJsonResponse(ctx, statusCode, response);
    }
}

