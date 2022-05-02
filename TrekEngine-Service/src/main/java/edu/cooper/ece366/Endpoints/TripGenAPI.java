package edu.cooper.ece366.Endpoints;

import static spark.Spark.*;

import com.google.gson.JsonObject;

import org.bson.types.ObjectId;
import java.util.ArrayList;

import edu.cooper.ece366.Mongo.Stops.BigStops.BigStopHandler;
import edu.cooper.ece366.Mongo.Stops.SmallStops.SmallStopHandler;
import edu.cooper.ece366.Mongo.Trips.Trip;
import edu.cooper.ece366.Mongo.Trips.TripHandler;
import edu.cooper.ece366.Mongo.Stops.BigStops.BigStops;
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

    public static void paths(TripHandler tripHandler, UserHandler userHandler, BigStopHandler bigStopHandler,
            SmallStopHandler smallStopHandler) {
        path("/tripgen", () -> {
            post("/allrecs", (BodyParserRoute) (req, res, body) -> {
                JsonObject json = body.getAsJsonObject();
                Trip trip = new Trip(json);

                return "hiya";
            });

            post("/userrecs", (BodyParserRoute) (req, res, body) -> {
                JsonObject json = body.getAsJsonObject();
                Trip trip = new Trip(json);

                return "user";
            });

            post("/autorecs", (BodyParserRoute) (req, res, body) -> {
                return "auto";
            });

            post("/unsafeInsert", (BodyParserRoute) (req, res, body) -> {
                Trip trip = new Trip(body.get("trip").getAsJsonObject());
                tripHandler.insert(trip);
                return "Insert Succesfull";
            });

            post("/insert", (AuthRoute) (req, res, body, user) -> {
                JsonObject tripJ = body.get("trip").getAsJsonObject();
                tripJ.get("meta").getAsJsonObject().addProperty("user", user.getEmail());
                String startLoc = tripJ.get("trip").getAsJsonObject().get("startLocation").getAsString();
                String endLoc = tripJ.get("trip").getAsJsonObject().get("endLocation").getAsString();
                BigStops start = bigStopHandler.getCuratedStopsByNameFuzzy(startLoc)[0];
                BigStops end = bigStopHandler.getCuratedStopsByNameFuzzy(endLoc)[0];
                // TODO set start and end locations to the object id for insertion

                // Trip trip = new Trip(tripJ);
                // tripHandler.insert(trip);
                return "Insert Succesfull";
            });

            post("/getById", (QAuthRoute) (req, res, body, user) -> {
                String id = body.get("trip_id").getAsString();
                Trip trip = tripHandler.getById(id);
                if (trip.getMeta().getIsPrivate() &&
                        (user == null || !user.getEmail().equals(trip.getMeta().getUser()))) {
                    res.status(400);
                    return "Trip is private";
                }
                // return trip.toJSONString(bigStopHandler,smallStopHandler);
                return trip.toJSONString();
            });

            post("/update", (AuthRoute) (req, res, body, user) -> {
                Trip trip = new Trip(body.get("trip").getAsJsonObject());
                // do checks first to make sure it is legal
                Trip currentTrip = tripHandler.getByIdAndUser(trip.getId(), user.getEmail());
                if (currentTrip == null || !currentTrip.getMeta().getUser().equals(user.getEmail())) {
                    res.status(400);
                    return "Unkown Error User doesn't have permission to edit this trip";
                }
                tripHandler.update(trip);
                return "Update Succesfull";
            });
        });
    }
}