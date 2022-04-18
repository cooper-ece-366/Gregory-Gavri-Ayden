package edu.cooper.ece366.Mongo.Stops;

import java.util.ArrayList;

import com.mongodb.client.model.Filters;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import edu.cooper.ece366.Mongo.CollectionHandler;
import edu.cooper.ece366.Mongo.MongoHandler;

public abstract class StopHandler<T extends Loacation> extends CollectionHandler<T>{

    public StopHandler(MongoHandler handler, String name, Class<T> clazz){
        super(handler, name, clazz);
    }

    protected Bson getStopsByTypeFilter(String tag){
        return Filters.eq("type", tag); 
    }

    public ArrayList<T> getStopsByType(String tag){
        return rawQuery(getStopsByTypeFilter(tag)); 
    }

    protected Bson getStopByLocFilter(double lnglb, double latlb, double lngup, double latup){
        return Filters.and(
            Filters.gte("lng", lnglb),
            Filters.lte("lng", lngup), 
            Filters.gte("lat", latlb), 
            Filters.lte("lat", latup)
        ); 
    }

    public ArrayList<T> getStopsByLoc(double lnglb, double latlb, double lngup, double latup){
        return rawQuery(getStopByLocFilter(lnglb, latlb, lngup, latup));
    }

    public ArrayList<T> getStopsByLocIgnore(double lnglb, double latlb, double lngup, double latup, T start, T end){
        
        return rawQuery(
            Filters.and(
                Filters.ne("_id", start.getId()),
                Filters.ne("_id", end.getId()),
                getStopByLocFilter(lnglb, latlb, lngup, latup)
            )
        );
    }

    public ArrayList<T> getStopsByLocIgnore(double lnglb, double latlb, double lngup, double latup, ObjectId start, ObjectId end){
        
        return rawQuery(
            Filters.and(
                Filters.ne("_id", start),
                Filters.ne("_id", end),
                getStopByLocFilter(lnglb, latlb, lngup, latup)
            )
        );
    }
    protected Bson getStopByNameFilter(String name){
        return Filters.eq("name", name); 
    }

    public ArrayList<T> getStopsByName(String name){
        return rawQuery(getStopByNameFilter(name));
    }
}
