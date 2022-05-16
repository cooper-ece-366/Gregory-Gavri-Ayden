// Written By Gregory Presser
package edu.cooper.ece366.Utils.TripAI;

import java.util.ArrayList;
import java.util.List;

import edu.cooper.ece366.Mongo.Stops.BigStops.BigStops;
import edu.cooper.ece366.Utils.GeoLocation.LngLat;

public class StopBox {
    public List<BigStops> stops;
    private LngLat[] boundingBox;

    public StopBox(List<BigStops> stops, LngLat[] boundingBox) {
        this.stops = stops;
        this.boundingBox = boundingBox;
    }

    public StopBox(StopBox b){
        this.stops = new ArrayList<BigStops>(b.stops);
        this.boundingBox = b.boundingBox;
    }
    
    
}
