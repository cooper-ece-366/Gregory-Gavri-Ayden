package edu.cooper.ece366.Mongo.Trips;

import java.util.ArrayList;

import com.mongodb.client.model.Filters;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import edu.cooper.ece366.Mongo.CollectionHandler;
import edu.cooper.ece366.Mongo.MongoHandler;
import edu.cooper.ece366.Mongo.Stops.BigStops.BigStopHandler;
import edu.cooper.ece366.Mongo.Stops.BigStops.BigStops; 


public class TripHandler extends CollectionHandler<Trip>{
    public TripHandler(MongoHandler handler) {
        super(handler,"trips", Trip.class); // creates protected collection 
    }

    public ArrayList<Trip> getTripByTag(String tag, double weight) {
        Bson filter = new Document().append("details.tags", 
                    new Document().append("$elemMatch",
                        new Document().append("tag", tag)
                        .append("weight", new Document().append("$gte", weight))));
        return this.rawQuery(filter); 
    }

    public ArrayList<Trip> getTripByTag(String tag){
        return getTripByTag(tag, 0.0); 
    }

    public ArrayList<Trip> getTripByUser(String userEmail){
        Bson filter = new Document().append("meta.user", userEmail); 
        return this.rawQuery(filter); 
    }

    public Trip getByIdAndUser(ObjectId id, String userEmail){
        Bson filter = new Document().append("_id", id).append("meta.user", userEmail); 
        ArrayList<Trip> trips = this.rawQuery(filter);
        return trips.size() > 0 ? trips.get(0) : null;
    }

    public ArrayList<Trip> getTripByLength(int length, boolean isGreaterThan, boolean isEqualTo){
        Bson filter = new Document().append("details.tripLength", isEqualTo ? length :
                            new Document().append("$" + (isGreaterThan ? "gte" : "lte"), length));
        return this.rawQuery(filter); 
    }

    private  ArrayList<Trip> getTripByLoc(String loc, boolean isStart,BigStopHandler stopHandler ){

        ArrayList<BigStops> stops = stopHandler.getStopsByName(loc); 
        ArrayList<ObjectId> stopIds = new ArrayList<ObjectId>();
        for(BigStops s : stops){
            stopIds.add(s.getId()); 
        }
        Bson filter = Filters.in("trip." + (isStart ? "startLocation" : "endLocation"), stopIds);
        return this.rawQuery(filter);
    }


    public ArrayList<Trip> getTripByStartLoc(String loc,BigStopHandler stopHandler){
        return getTripByLoc(loc, true,stopHandler);
    }

    public ArrayList<Trip> getTripByEndLoc(String loc,BigStopHandler stopHandler){
        return getTripByLoc(loc, false,stopHandler);
    }

}