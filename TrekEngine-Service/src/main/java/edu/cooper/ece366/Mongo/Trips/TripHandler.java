package edu.cooper.ece366.Mongo.Trips;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.conversions.Bson;

import edu.cooper.ece366.Mongo.CollectionHandler;
import edu.cooper.ece366.Mongo.MongoHandler; 


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

    public ArrayList<Trip> getTripByLength(int length, boolean isGreaterThan, boolean isEqualTo){
        Bson filter = new Document().append("details.tripLength", isEqualTo ? length :
                            new Document().append("$" + (isGreaterThan ? "gte" : "lte"), length));
        return this.rawQuery(filter); 
    }

    private ArrayList<Trip> getTripByLoc(String loc, boolean isStart){
        Bson filter = new Document().append("trip." + (isStart ? "startLocation" : "endLocation") + ".name",loc);  
        return this.rawQuery(filter);
    }

    private ArrayList<Trip> getTripByLoc(double lnglb, double lngup, double latlb, double latup, boolean isStart){
        
        String base = "trip." + (isStart ? "startLocation" : "endLocation"); 
        Bson filter = new Document().append(base + ".lng", new Document().append("$gte", lnglb).append("$lte",lngup) )
                                    .append(base + ".lat", new Document().append("$gte",latlb).append("$lte",latup) );
        return this.rawQuery(filter);
    }

    public ArrayList<Trip> getTripByStartLoc(String loc){
        return getTripByLoc(loc, true);
    }
    public ArrayList<Trip> getTripByStartLoc(double lnglb, double lngup, double latlb, double latup){
        return getTripByLoc( lnglb, lngup, latlb, latup, true); 
    }

    public ArrayList<Trip> getTripByEndLoc(String loc){
        return getTripByLoc(loc, false);
    }
    public ArrayList<Trip> getTripByEndLoc(double lnglb, double lngup, double latlb, double latup){
        return getTripByLoc( lnglb, lngup, latlb, latup, false); 
    }

}