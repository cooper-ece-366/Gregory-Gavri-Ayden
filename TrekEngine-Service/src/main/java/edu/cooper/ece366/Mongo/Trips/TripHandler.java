package edu.cooper.ece366.Mongo.Trips;

import java.util.ArrayList;

import com.mongodb.client.model.Filters;

import org.bson.Document;
import org.bson.types.ObjectId;

import edu.cooper.ece366.Mongo.CollectionHandler;
import edu.cooper.ece366.Mongo.MongoHandler;


public class TripHandler extends CollectionHandler{
    public TripHandler(MongoHandler handler) {
        super(handler,"trips"); // creates protected collection 
    }

    public void insertTrip(String name){
        collection.insertOne(new Document().append("_id","123").append("name", name)); 
    }

    // returns trip from tripId
    public Trip getTripFromID(String tripId) {
        ArrayList<Trip> trips = collection.find(Filters.eq("_id",new ObjectId(tripId)), Trip.class).into(new ArrayList<>());
        return trips.size() > 0 ? trips.get(0) : null;
    }
}