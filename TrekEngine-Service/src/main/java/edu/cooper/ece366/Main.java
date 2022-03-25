package edu.cooper.ece366;

import static spark.Spark.*;

import edu.cooper.ece366.Mongo.MongoHandler;
import edu.cooper.ece366.Mongo.User.UserHandler;
import edu.cooper.ece366.Utils.GeoLocationHandler;

import static edu.cooper.ece366.RouteInterfaces.UserBodyParser.setUserHandler;
import static edu.cooper.ece366.RouteInterfaces.UserBodyParser.AuthRoute;
import edu.cooper.ece366.Endpoints.TripGenAPI;

public class Main {

    private static UserHandler userHandler;
    private static GeoLocationHandler geoHandler; 

    private static void enableCORS() {

        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            // Note: this may or may not be necessary in your particular application
            response.type("application/json");
        });
    }

    public static void init() {
        MongoHandler mongoHandler = new MongoHandler();
        userHandler = new UserHandler(mongoHandler);
<<<<<<< HEAD
        setUserHandler(userHandler); // initalizes AuthRoute to work properly with the userHandler 
        geoHandler = new GeoLocationHandler(); 
=======
        setUserHandler(userHandler); // initalizes AuthRoute to work properly with the userHandler
>>>>>>> Greg-MapGUI
        enableCORS();
    }

    public static void paths() {
        // login post request authenticator and returns a user object to the client
        path("/user", () -> {
            post("/me", (AuthRoute) (req, res, body, user) -> user.toJSONString());
        });
    }

    public static void main(String[] args) {
        init();
        path("/api/v1", Main::paths);
        path("/api/v1", TripGenAPI::paths);
    }
}
