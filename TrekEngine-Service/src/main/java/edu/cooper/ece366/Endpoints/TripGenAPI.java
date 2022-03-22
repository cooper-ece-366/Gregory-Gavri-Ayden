package edu.cooper.ece366.Endpoints;

import static spark.Spark.*;

public class TripGenAPI {
    public static void paths() {
        // login post request authenticator and returns a user object to the client
        path("/tripgen", () -> {
            post("/submit", (req, res) -> "Success");
        });
    }
}
