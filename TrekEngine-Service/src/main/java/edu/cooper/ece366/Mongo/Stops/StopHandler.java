package edu.cooper.ece366.Mongo.Stops;

import java.util.ArrayList;

import com.mongodb.client.model.Filters;

import org.bson.conversions.Bson;

import edu.cooper.ece366.Mongo.CollectionHandler;
import edu.cooper.ece366.Mongo.MongoHandler;
import com.mongodb.client.model.TextSearchOptions;

public abstract class StopHandler<T extends Loacation> extends CollectionHandler<T> {

    public StopHandler(MongoHandler handler, String name, Class<T> clazz) {
        super(handler, name, clazz);
    }

    protected Bson getStopsByTypeFilter(String tag) {
        return Filters.eq("type", tag);
    }

    public ArrayList<T> getStopsByType(String tag) {
        return rawQuery(getStopsByTypeFilter(tag));
    }

    protected Bson getStopByLocFilter(double lnglb, double latlb, double lngup, double latup) {
        return Filters.and(Filters.gte("lng", lnglb), Filters.lte("lng", lngup), Filters.gte("lat", latlb),
                Filters.lte("lat", latup));
    }

    public ArrayList<T> getStopsByLoc(double lnglb, double latlb, double lngup, double latup) {
        return rawQuery(getStopByLocFilter(lnglb, latlb, lngup, latup));
    }

    protected Bson getStopByNameFilter(String name) {
        return Filters.eq("name", name);
    }

    protected Bson getStopByNameFilterFuzzy(String name) {
        return Filters.eq("name", name);
    }

    public ArrayList<T> getStopsByName(String name) {
        return rawQuery(getStopByNameFilter(name));
    }
}
