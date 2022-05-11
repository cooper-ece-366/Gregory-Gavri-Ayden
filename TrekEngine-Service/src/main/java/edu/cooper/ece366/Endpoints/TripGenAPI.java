// Written by Greg Presser and Gavri Kepets
package edu.cooper.ece366.Endpoints;

import static spark.Spark.*;

// Written By Gavri Kepets
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.lang.Exception;
import edu.cooper.ece366.Mongo.Stops.BigStops.BigStopHandler;
import edu.cooper.ece366.Mongo.Stops.SmallStops.SmallStopHandler;
import edu.cooper.ece366.Mongo.Trips.Trip;
import edu.cooper.ece366.Mongo.Trips.TripHandler;
import edu.cooper.ece366.Mongo.Stops.BigStops.BigStops;
import edu.cooper.ece366.Mongo.Trips.Stop;
import edu.cooper.ece366.Mongo.User.UserHandler;
import edu.cooper.ece366.RouteInterfaces.BodyParserRoute;
import edu.cooper.ece366.RouteInterfaces.UserBodyParser.AuthRoute;
import edu.cooper.ece366.RouteInterfaces.UserBodyParser.QAuthRoute;
import edu.cooper.ece366.Utils.TripAI.TripGenerator;

public class TripGenAPI {
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
                String trip_id = body.get("trip_id").getAsString();
                Trip trip = tripHandler.getById(trip_id);

                try {
                    TripGenerator tripGenerator = new TripGenerator(trip, bigStopHandler, 6);
                    Trip newTrip = tripGenerator.generateTrip();
                    return newTrip.toJSONString(bigStopHandler, smallStopHandler);
                } catch (Exception e) {
                    return "error";
                }
            });

            post("/insert", (AuthRoute) (req, res, body, user) -> {
                JsonObject tripJ = body.get("trip").getAsJsonObject();
                tripJ.get("meta").getAsJsonObject().addProperty("user", user.getEmail());
                String startLoc = tripJ.get("trip").getAsJsonObject().get("startLocation").getAsString();
                String endLoc = tripJ.get("trip").getAsJsonObject().get("endLocation").getAsString();

                ArrayList<BigStops> startBigStops = bigStopHandler.getCuratedStopsByNameFuzzy(startLoc);
                ArrayList<BigStops> endBigStops = bigStopHandler.getCuratedStopsByNameFuzzy(endLoc);
                ArrayList<BigStops> allStops = new ArrayList<BigStops>();
                JsonArray stops = tripJ.get("trip").getAsJsonObject().get("stops").getAsJsonArray();

                BigStops startBigStop = null;
                BigStops endBigStop = null;
                ArrayList<Stop> bigStops = new ArrayList<Stop>();

                if (startBigStops.size() == 0) {
                    // TODO: add it to the database
                } else {
                    startBigStop = startBigStops.get(0);
                }

                if (endBigStops.size() == 0) {
                    // TODO: add it to the database
                } else {
                    endBigStop = endBigStops.get(0);
                }

                for (int i = 0; i < stops.size(); i++) {
                    String stopName = stops.get(i).getAsString();
                    ArrayList<BigStops> reqStops = bigStopHandler.getCuratedStopsByNameFuzzy(stopName);

                    if (reqStops.size() == 0) {
                        // TODO: add it to the database
                    } else {
                        Stop stopObj = new Stop(reqStops.get(0).getId());
                        bigStops.add(stopObj);
                    }
                }

                Trip trip = new Trip(tripJ, true);
                trip.getTripData().setStartLocation(startBigStop.getId());
                trip.getTripData().setEndLocation(endBigStop.getId());
                trip.getTripData().setStops(bigStops);

                String id = tripHandler.insertTrip(trip);

                return id;
            });

            post("/getById", (QAuthRoute) (req, res, body, user) -> {
                String id = body.get("trip_id").getAsString();
                Trip trip = tripHandler.getById(id);
                if (trip.getMeta().getIsPrivate() &&
                        (user == null || !user.getEmail().equals(trip.getMeta().getUser()))) {
                    res.status(400);
                    return "Trip is private";
                }
                return trip.toJSONString(bigStopHandler, smallStopHandler);
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