package edu.cooper.ece366;

import static spark.Spark.*;
import edu.cooper.ece366.Mongo.MongoHandler;
import edu.cooper.ece366.Mongo.User.UserHandler;

public class Main {

    private static UserHandler userHandler;

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

    public static void paths() {
        get("/", (req,res) -> "Hello World");

        // login post request authenticator
        post("/login", (req,res) -> {
            return userHandler.verifyUser(req.body());
        });
    }

    public static void main(String[] args) {
        enableCORS();
        MongoHandler mongoHandler = new MongoHandler();
        userHandler = new UserHandler(mongoHandler);
        paths();

    }
}
