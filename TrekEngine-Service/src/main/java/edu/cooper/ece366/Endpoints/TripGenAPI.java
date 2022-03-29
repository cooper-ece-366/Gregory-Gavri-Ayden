package edu.cooper.ece366.Endpoints;

import static spark.Spark.*;

import edu.cooper.ece366.Mongo.Trips.TripHandler;
import edu.cooper.ece366.Mongo.User.UserHandler;

public class TripGenAPI {

    static String GetRelevantTrips() {
        // This function will eventually get a mix of relevant Trips from friends,
        // popular users, and auto generated trips.
        return "trips trips blah blah blah";
    }

    public static void paths(TripHandler tripHandler, UserHandler userHandler) {
        // login post request authenticator and returns a user object to the client
        path("/tripgen", () -> {
            post("/submit", (req, res) -> GetRelevantTrips());
        });
    }
}