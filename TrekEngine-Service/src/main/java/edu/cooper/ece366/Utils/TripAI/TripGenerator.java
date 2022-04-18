package edu.cooper.ece366.Utils.TripAI;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import edu.cooper.ece366.Mongo.Stops.BigStops.BigStopHandler;
import edu.cooper.ece366.Mongo.Stops.BigStops.BigStops;
import edu.cooper.ece366.Mongo.Trips.Trip;
import edu.cooper.ece366.Utils.GeoLocationHandler;

public class TripGenerator {

    private BigStopHandler stopHandler;
    private GeoLocationHandler geoHandler;

    private long minTripLen; 
    private List<BigStops> stops; 
    private Trip templateTrip; 

    private class Generator {
        private List<BigStops> stopOptions;
        private int initalTimeAlpha; 
        private int initalWeightAlpha; 
        private int doubleDelta; 

        public Generator(List<BigStops> stopOptions){
            this(stopOptions, 10, 10, 1);
        }
        public Generator(List<BigStops> stopOptions, int initalTimeAlpha, int initalWeightAlpha, int doubleDelta){
            this.stopOptions = stopOptions;
            this.initalTimeAlpha = initalTimeAlpha;
            this.initalWeightAlpha = initalWeightAlpha;
            this.doubleDelta = doubleDelta; 
        }

        public Trip generate(){
            boolean criteria_met = false;
            int timeAlpha = initalTimeAlpha;
            int weightAlpha = initalWeightAlpha;
            while(criteria_met){
                iterate(timeAlpha,weightAlpha); 
                timeAlpha -= doubleDelta;
                weightAlpha -= doubleDelta;
            }

            return null; 

        }

        public void iterate(int timeAlpha, int weightAlpha){

        }

    }


    public TripGenerator(Trip templateTrip,BigStopHandler stopHandler, int stopsPerDay){
        this.templateTrip = templateTrip;
        this.stopHandler = stopHandler;
        this.geoHandler = new GeoLocationHandler();
        this.minTripLen = calculateMinTripLen(); 
        this.stops = getStops(stopsPerDay); 
    }

    public Trip generateTrip(){
        return new Generator(stops).generate(); 

    }

    private static int calculateScore(List<BigStops> stops){
        return 0; 
    }

    private static int calculateScore(List<BigStops> stops, int indexIgnore){
        stops.remove(indexIgnore);
        return calculateScore(stops);
    }

    // Prob don't use 
    private static int calculateScore(List<BigStops> stops, BigStops stopToExclue){
        stops.remove(stopToExclue);
        return calculateScore(stops);
    }


    // TODO implement 
    private long calculateMinTripLen(){
        return 0; 
    }

    private ArrayList<BigStops> getStops(int stopsPerDay){
        int[] bounds = getBoundingBox(); 
        int lnglb = bounds[0];
        int latlb = bounds[1];
        int lngup = bounds[2];
        int latup = bounds[3];  

        ObjectId startLoc = templateTrip.getTripData().getStartLocation();
        ObjectId endLoc = templateTrip.getTripData().getEndLocation();

        return stopHandler.getStopsByLocIgnore(lnglb, latlb, lngup, latup,startLoc,endLoc); 
    }

    // TODO implement
    // @returns the first element is the lnglb, the second is the latlb, the third is the lngub, the fourth is the latub
    private int[] getBoundingBox(){
        return new int[4]; 
    }

    
}
