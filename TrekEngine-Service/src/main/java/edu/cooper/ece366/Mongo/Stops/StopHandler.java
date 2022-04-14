package edu.cooper.ece366.Mongo.Stops;

import java.util.ArrayList;

import com.mongodb.client.model.Filters;

import edu.cooper.ece366.Mongo.CollectionHandler;
import edu.cooper.ece366.Mongo.MongoHandler;

public abstract class StopHandler<T extends Loacation> extends CollectionHandler<T>{

    public StopHandler(MongoHandler handler, String name, Class<T> clazz){
        super(handler, name, clazz);
    }

    public ArrayList<T> getStopsByType(String tag){
        return rawQuery(Filters.eq("type", tag)); 
    }

    public ArrayList<T> getStopsByLoc(double lnglb, double latlb, double lngup, double latup){
        return rawQuery(Filters.and(Filters.gte("lng", lnglb), Filters.lte("lng", lngup), Filters.gte("lat", latlb), Filters.lte("lat", latup)));
    }

    public ArrayList<T> getStopsByName(String name){
        return rawQuery(Filters.eq("name", name));
    }
}
