// Written By Gregory Presser
package edu.cooper.ece366.Utils.TripAI;

import java.util.Comparator;

import edu.cooper.ece366.Mongo.Stops.BigStops.BigStops;
import edu.cooper.ece366.Utils.GeoLocation.LngLat;

public class SortBigStops {
    
    private LngLat ref; 
    public SortBigStops(BigStops ref){
        this.ref = ref.toLngLat(); 
    }
    public SortBigStops(LngLat ref){
        this.ref = ref; 
    }


    private class Sort implements Comparator<BigStops> {
        @Override
        public int compare(BigStops o1, BigStops o2) {
            return Double.compare(o1.toLngLat().sub(ref).abs(),o2.toLngLat().sub(ref).abs());
        }
    }

    public Sort getSort(){
        return new Sort(); 
    }
}
