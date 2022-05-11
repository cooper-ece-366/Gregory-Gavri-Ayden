package edu.cooper.ece366;

import static spark.Spark.*;

import edu.cooper.ece366.Mongo.MongoHandler;
import edu.cooper.ece366.Mongo.Stops.BigStops.BigStopHandler;
import edu.cooper.ece366.Mongo.Stops.SmallStops.SmallStopHandler;
import edu.cooper.ece366.Mongo.Trips.TripHandler;
import edu.cooper.ece366.Mongo.User.UserHandler;
import edu.cooper.ece366.RouteInterfaces.UserBodyParser;
import edu.cooper.ece366.Utils.GeoLocation.GeoLocationHandler;
import edu.cooper.ece366.Endpoints.TripGenAPI;
import edu.cooper.ece366.Endpoints.GeoLocAPI;
import edu.cooper.ece366.Endpoints.UserAPI;

public class Main {

    private static UserHandler userHandler;
    private static GeoLocationHandler geoHandler; 
    private static TripHandler tripHandler;
    private static BigStopHandler bigStopHandler;
    private static SmallStopHandler smallStopHandler;

    // Written By Gregory Presser
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

    // Written By Gregory Presser
    public static void init() {
        MongoHandler mongoHandler = new MongoHandler();
        userHandler = new UserHandler(mongoHandler);
        UserBodyParser.setUserHandler(userHandler); // initalizes AuthRoute to work properly with the userHandler 
        tripHandler = new TripHandler(mongoHandler);
        bigStopHandler = new BigStopHandler(mongoHandler);
        smallStopHandler = new SmallStopHandler(mongoHandler);
        geoHandler = new GeoLocationHandler(); 
        enableCORS();
    }

    // Written by Everyone
    public static void paths() {
        // login post request authenticator and returns a user object to the client
        UserAPI.paths(userHandler); 
        TripGenAPI.paths(tripHandler,userHandler,bigStopHandler,smallStopHandler); 
        GeoLocAPI.paths(geoHandler); 
    }

    public static void main(String[] args) {
        init();
        path("/api/v1", Main::paths);
    }
}
