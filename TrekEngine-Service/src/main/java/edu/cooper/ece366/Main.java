package edu.cooper.ece366;

import static spark.Spark.*;

import edu.cooper.ece366.Mongo.MongoHandler;
import edu.cooper.ece366.Mongo.User.UserHandler;
import edu.cooper.ece366.Utils.GeoLocationHandler;

import static edu.cooper.ece366.RouteInterfaces.UserBodyParser.setUserHandler;
import edu.cooper.ece366.Endpoints.TripGenAPI;
import edu.cooper.ece366.Endpoints.GeoLocAPI;
import edu.cooper.ece366.Endpoints.UserAPI;

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
        setUserHandler(userHandler); // initalizes AuthRoute to work properly with the userHandler 
        geoHandler = new GeoLocationHandler(); 
        enableCORS();
    }

    public static void paths() {
        // login post request authenticator and returns a user object to the client
        UserAPI.paths(userHandler); 
        TripGenAPI.paths(); 
        GeoLocAPI.paths(geoHandler); 
    }

    public static void main(String[] args) {
        init();
        path("/api/v1", Main::paths);
    }
}
