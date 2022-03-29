package edu.cooper.ece366.Endpoints;

import static spark.Spark.*;

import edu.cooper.ece366.Mongo.Trips.TripHandler;

public class TripGenAPI {

    static String GetRelevantTrips() {
        // This function will eventually get a mix of relevant Trips from friends,
        // popular users, and auto generated trips.
        return "trips trips blah blah blah";
    }

    public static void paths(TripHandler tripHandler) {
        // login post request authenticator and returns a user object to the client
        path("/tripgen", () -> {
            post("/submit", (req, res) -> GetRelevantTrips());
            get("/getById", (req,res) -> {
                System.out.println(tripHandler.getTripFromID(req.queryParams("tripId")).toJSONString());
                return tripHandler.getTripFromID(req.queryParams("tripId")).toJSONString(); 
            });
            get("/insert", (req,res) -> {
                tripHandler.insertTrip(req.queryParams("name"));
                return "hi"; 
            }); 
        });
    }
}