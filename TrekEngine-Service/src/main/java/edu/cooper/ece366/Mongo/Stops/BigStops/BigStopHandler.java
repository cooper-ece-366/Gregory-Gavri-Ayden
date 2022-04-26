package edu.cooper.ece366.Mongo.Stops.BigStops;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.client.model.Filters;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import edu.cooper.ece366.Mongo.MongoHandler;
import edu.cooper.ece366.Mongo.Stops.StopHandler;
import edu.cooper.ece366.Utils.GeoLocation.LngLat;
import io.opencensus.trace.export.RunningSpanStore.Filter;

public class BigStopHandler extends StopHandler<BigStops> {

    public BigStopHandler(MongoHandler handler) {
        super(handler, "bigStops", BigStops.class);
    }

    private Bson isCuratedFilter() {
        return Filters.eq("isCurated", true);
    }

    private Bson andModifier(Bson filter){
        return Filters.and(filter,isCuratedFilter() );
    }
    
    public ArrayList<BigStops> getCuratedStopsByType(String type){
        Bson filter = getStopsByTypeFilter(type);
        return rawQuery(andModifier(filter));  
    }
    public ArrayList<BigStops> getCuratedStopsByName(String name){
        Bson filter = getStopByNameFilter(name); 
        return rawQuery(andModifier(filter));
    }

    public ArrayList<BigStops> getCuratedStopsInGeoP(ArrayList<ArrayList<Double>> polygon){
        Bson filter = getStopsInGeoPFilter(polygon);
        return rawQuery(andModifier(filter));
    }
    public ArrayList<BigStops> getCuratedStopsInGeoP(LngLat[] polygon){
        Bson filter = getStopsInGeoPFilterL(polygon);
        return rawQuery(andModifier(filter));
    }

    public ArrayList<BigStops> getCuratedRandomStopsInGeoP(LngLat[] polygon, int size){
        Bson filter = andModifier(getStopsInGeoPFilterL(polygon));
        return aggregateRandomWithFilter(filter, size); 
    }

    public ArrayList<BigStops> getCuratedRandomStopsInGeoP(ArrayList<ArrayList<Double>> polygon, int size){
        Bson filter = andModifier(getStopsInGeoPFilter(polygon));
        return aggregateRandomWithFilter(filter, size); 
    }

    public ArrayList<BigStops> getCuratedRandomStopsInGeoPWR(LngLat[] polygon, int size, List<ObjectId> include){
        Bson filter = andModifier(getStopsInGeoPFilterL(polygon));
        ArrayList<BigStops> l1 = aggregateRandomWithFilter(filter, size); 
        Bson including[] = new Bson[include.size()]; 
        for(int i = 0; i<include.size(); i++){
            including[i] = Filters.eq("_id", include.get(i));
        }
        ArrayList<BigStops> l2 = rawQuery(Filters.and(getStopsInGeoPFilterL(polygon),isCuratedFilter(), Filters.or(including)));
        
        for(BigStops stop: l2){
            if(!l1.contains(stop)){
                l1.add(stop);
            }
        }
        return l1;

    }



}

