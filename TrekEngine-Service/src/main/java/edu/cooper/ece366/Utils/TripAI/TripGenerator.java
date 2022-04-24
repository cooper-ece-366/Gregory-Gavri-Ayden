package edu.cooper.ece366.Utils.TripAI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import edu.cooper.ece366.Exceptions.InvalidTripException;
import edu.cooper.ece366.Mongo.Stops.BigStops.BigStopHandler;
import edu.cooper.ece366.Mongo.Stops.BigStops.BigStops;
import edu.cooper.ece366.Mongo.Trips.Trip;
import edu.cooper.ece366.Utils.GeoLocation.GeoLocationHandler;
import edu.cooper.ece366.Utils.GeoLocation.LngLat;


public class TripGenerator {

    private BigStopHandler stopHandler;
    private GeoLocationHandler geoHandler;

    private long minTripLen; 
    private List<BigStops> stops; 
    private Trip templateTrip; 
    private LngLat[] boundingBox; // LngLat[2] = [lower, upper]

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

        // TODO implement
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
        
        // TODO implement 
        public void iterate(int timeAlpha, int weightAlpha){

        }

    }


    public TripGenerator(Trip templateTrip,BigStopHandler stopHandler, int distPerDay){
        this.templateTrip = templateTrip;
        this.stopHandler = stopHandler;
        this.geoHandler = new GeoLocationHandler();
        this.minTripLen = calculateMinTripLen(); 
        this.boundingBox = getBoundingBox();
        this.stops = getStops(distPerDay); 
    }

    public long getMinTripLen() {
        return minTripLen;
    }

    public Trip generateTrip(){
        return new Generator(stops).generate(); 
    }

    // TODO implement
    private static int calculateScore(List<BigStops> stops){
        return 0; 
    }

    private static int calculateScore(List<BigStops> stops, int indexIgnore){
        stops.remove(indexIgnore);
        return calculateScore(stops);
    }

    private long calculateMinTripLen(){

        ArrayList<String> list = new ArrayList<String>(){{
            BigStops start = stopHandler.getById(templateTrip.getTripData().getStartLocation()); 
            BigStops end = stopHandler.getById(templateTrip.getTripData().getEndLocation()); 
            add(start.getLat() + " " + start.getLng());
            add(end.getLat() + " " + end.getLng()); 
        }};
        try {
            return geoHandler.directions(list).getDurtaionS(); 
        } catch (IOException e) {
            e.printStackTrace();
            throw new InvalidTripException(); 
        }
    }

    private ArrayList<BigStops> getStops(int distPerDay){

        ObjectId startLoc = templateTrip.getTripData().getStartLocation();
        ObjectId endLoc = templateTrip.getTripData().getEndLocation();

        return stopHandler.getCuratedStopsInGeoP(boundingBox); 
    }

    // This fucntion will return the bounding box of
    // This boudning box will be approximite 
    // @returns the first element is the lnglb, the second is the latlb, the third is the lngub, the fourth is the latub
    private LngLat[] getBoundingBox(){

        System.out.println("minTripLen: " + minTripLen);
        final int speed = 27; // ~60 miles per hour in m/s

        final double maxSeconds = (0.125)*TripGeneratorUtils.convertDaysToSeconds(templateTrip.getDetails().getTripLength()); 
        final long delta = ((long)Math.floor(maxSeconds)) - minTripLen;
        
        System.out.println("delta: " + delta);
        // s * m/s = m 
        final long height = (delta/2)*speed;  // this is the height over the horizontal in meters

        System.out.println("height: " + height);
        LngLat start = templateTrip.getTripData().getStartLocation(stopHandler).toLngLat(); 
        LngLat end = templateTrip.getTripData().getEndLocation(stopHandler).toLngLat();
        
        if(start.getLng() > end.getLng()) {
            LngLat temp = start; 
            start = end; 
            end = temp; 
        }

        System.out.println("start: " + start);
        System.out.println("end: " + end);

        final double angle = start.getBearing(end); 
        final double angleUp = angle + (Math.PI/2); 
        final double angleDown = angle - (Math.PI/2); 

        System.out.println("Angle: " + angle);
        System.out.println("AngleUp: " + angleUp);
        System.out.println("AngleDown: " + angleDown);

        LngLat A = start.getDest(height, angleUp); 
        LngLat B = start.getDest(height, angleDown);
        LngLat C = end.getDest(height, angleDown); 
        LngLat D = end.getDest(height, angleUp); 

        System.out.println("A: " + A);
        System.out.println("B: " + B);
        System.out.println("C: " + C);
        System.out.println("D: " + D);

        LngLat[] l = {A,B,C,D}; 

        return l; 

    }

    
}
