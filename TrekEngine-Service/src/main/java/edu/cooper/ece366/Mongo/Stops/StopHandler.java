package edu.cooper.ece366.Mongo.Stops;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import edu.cooper.ece366.Mongo.CollectionHandler;
import edu.cooper.ece366.Mongo.MongoHandler;
import edu.cooper.ece366.Utils.GeoLocation.LngLat;

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

    protected Bson getStopsInGeoPFilterL(LngLat[] locs){
        ArrayList<ArrayList<Double>> polygon = new ArrayList<ArrayList<Double>>();
        for(LngLat loc : locs){
            polygon.add(loc.getList());
        }
        return getStopsInGeoPFilter(polygon);
    }

    protected Bson getStopsInGeoPFilter(ArrayList<ArrayList<Double>> polygon){
        return new Document().append("cords",
            new Document().append("$geowithin", 
                new Document().append("$geometry", 
                    new Document().append("type", "Polygon")
                    .append("coordinates",polygon
            )))
        ); 
    }

    public ArrayList<T> getStopsInGeoP(ArrayList<ArrayList<Double>> polygon){
        return rawQuery(getStopsInGeoPFilter(polygon));
    }
    public ArrayList<T> getStopsInGeoP(LngLat[] polygon){
        return rawQuery(getStopsInGeoPFilterL(polygon));
    }

    protected Bson getStopByNameFilter(String name){
        return Filters.eq("name", name); 
    }

    public ArrayList<T> getStopsByName(String name){
        return rawQuery(getStopByNameFilter(name));
    }
}
