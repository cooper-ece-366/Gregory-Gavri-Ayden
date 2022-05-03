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
import com.mongodb.client.model.TextSearchOptions;
import edu.cooper.ece366.Utils.GeoLocation.LngLat;


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

      
    protected Bson getStopsInGeoPFilterL(LngLat[] locs){
        ArrayList<ArrayList<Double>> polygon = new ArrayList<ArrayList<Double>>();
        for(LngLat loc : locs){
            polygon.add(loc.getList());
        }
        return getStopsInGeoPFilter(polygon);
    }

    protected Bson getStopsInGeoPFilter(ArrayList<ArrayList<Double>> polygon){
        polygon.add(polygon.get(0)); // close the loop 
        ArrayList<ArrayList<ArrayList<Double>>> p = new ArrayList<ArrayList<ArrayList<Double>>>();
        p.add(polygon);
        return new Document().append("cords",
            new Document().append("$geoWithin", 
                new Document().append("$geometry", 
                    new Document().append("type", "Polygon")
                    .append("coordinates",p
            )))
        ); 
    }

    public ArrayList<T> getStopsInGeoP(ArrayList<ArrayList<Double>> polygon){
        return rawQuery(getStopsInGeoPFilter(polygon));
    }
    public ArrayList<T> getStopsInGeoP(LngLat[] polygon){
        return rawQuery(getStopsInGeoPFilterL(polygon));
    }


    public ArrayList<T> getStopsByName(String name) {
        return rawQuery(getStopByNameFilter(name));
    }

    public ArrayList<T> aggregateRandomWithFilter(Bson filter, int size){
        return collection.aggregate(Arrays.asList(
            Aggregates.match(filter),
            Aggregates.sample(size)
        )).into(new ArrayList<>());
    }

    public ArrayList<T> geoWithSample(int size){
        return aggregateRandomWithFilter(Filters.eq("type", "stop"), size);
    }
}
