package edu.cooper.ece366.Endpoints;

import static spark.Spark.*;

import com.google.gson.JsonObject;

import edu.cooper.ece366.Mongo.Trips.Trip;
import edu.cooper.ece366.Mongo.Trips.TripHandler;
import edu.cooper.ece366.Mongo.User.UserHandler;
import edu.cooper.ece366.RouteInterfaces.BodyParserRoute;
import edu.cooper.ece366.RouteInterfaces.UserBodyParser.AuthRoute;
import edu.cooper.ece366.RouteInterfaces.UserBodyParser.QAuthRoute;

public class TripGenAPI {

    static String GetRelevantTrips() {
        // This function will eventually get a mix of relevant Trips from friends,
        // popular users, and auto generated trips.
        return "trips trips blah blah blah";
    }

    public static void paths(TripHandler tripHandler,UserHandler userHandler) {
        path("/tripgen", () -> {
            post("/submit", (req, res) -> GetRelevantTrips());
            
            post("/unsafeInsert", (BodyParserRoute)(req,res,body)->{
                Trip trip = new Trip(body.get("trip").getAsJsonObject()); 
                tripHandler.insert(trip);
                return "Insert Succesfull";
            }); 

            post("/insert", (AuthRoute)(req,res,body,user)->{
                JsonObject tripJ = body.get("trip").getAsJsonObject(); 
                tripJ.get("meta").getAsJsonObject().addProperty("user",user.getEmail());
                Trip trip = new Trip(body); 
                tripHandler.insert(trip);
                return "Insert Succesfull";
            });

            post("/getById", (QAuthRoute)(req,res,body,user)->{
                String id = body.get("trip_id").getAsString(); 
                Trip trip = tripHandler.getById(id); 
                if(trip.getMeta().getIsPrivate() && 
                        (user == null || !user.getEmail().equals(trip.getMeta().getUser()))) {
                    res.status(400);
                    return "Trip is private";
                }
                return trip.toJSONString();
            });
        }); 
    }
}