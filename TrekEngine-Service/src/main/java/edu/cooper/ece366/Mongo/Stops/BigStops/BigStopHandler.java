package edu.cooper.ece366.Mongo.Stops.BigStops;

import java.util.ArrayList;

import com.mongodb.client.model.Filters;

import org.bson.conversions.Bson;

import edu.cooper.ece366.Mongo.MongoHandler;
import edu.cooper.ece366.Mongo.Stops.StopHandler;

public class BigStopHandler extends StopHandler<BigStops> {

    public BigStopHandler(MongoHandler handler) {
        super(handler, "bigStops", BigStops.class);
    }

    private Bson andModifier(Bson filter){
        return Filters.and(filter,Filters.eq("isCurated", true) );
    }
    
    public ArrayList<BigStops> getCuratedStopsByType(String type){
        Bson filter = getStopsByTypeFilter(type);
        return rawQuery(andModifier(filter));  
    }
    public ArrayList<BigStops> getCuratedStopsByLoc(double lnglb, double latlb, double lngup, double latup){
        Bson filter = getStopByLocFilter(lnglb, latlb, lngup, latup);
        return rawQuery(andModifier(filter));
    }
    public ArrayList<BigStops> getCuratedStopsByName(String name){
        Bson filter = getStopByNameFilter(name); 
        return rawQuery(andModifier(filter));
    }



}

