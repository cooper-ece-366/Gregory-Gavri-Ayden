package edu.cooper.ece366.Utils.TripAI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.cooper.ece366.Exceptions.InvalidTripException;
import edu.cooper.ece366.Mongo.Stops.BigStops.BigStopHandler;
import edu.cooper.ece366.Mongo.Stops.BigStops.BigStops;
import edu.cooper.ece366.Mongo.Trips.Trip;
import edu.cooper.ece366.Utils.GeoLocation.DirData;
import edu.cooper.ece366.Utils.GeoLocation.GeoLocationHandler;
import edu.cooper.ece366.Utils.GeoLocation.LngLat;


public class TripGenerator {

    private BigStopHandler stopHandler;
    private GeoLocationHandler geoHandler;

    private DirData minTripLen; 
    private List<StopBox> stops; 
    private Trip templateTrip; 

    private class Generator {
        private List<StopBox> stopOptions;
        private int initalTimeAlpha; 
        private int initalWeightAlpha; 
        private int doubleDelta; 

        public Generator(List<StopBox> stopOptions){
            this(stopOptions, 10, 10, 1);
        }
        public Generator(List<StopBox> stopOptions, int initalTimeAlpha, int initalWeightAlpha, int doubleDelta){
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
        this.stops = getStops(distPerDay); 
    }

    public DirData getMinTripLen() {
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

    private DirData calculateMinTripLen(){

        ArrayList<String> list = new ArrayList<String>(){{
            BigStops start = stopHandler.getById(templateTrip.getTripData().getStartLocation()); 
            BigStops end = stopHandler.getById(templateTrip.getTripData().getEndLocation()); 
            add(start.getLat() + " " + start.getLng());
            add(end.getLat() + " " + end.getLng()); 
        }};
        try {
            return geoHandler.directions(list);  
        } catch (IOException e) {
            e.printStackTrace();
            throw new InvalidTripException(); 
        }
    }

    private ArrayList<StopBox> getStops(int distPerDay){
        ArrayList<StopBox> stopBoxes = new ArrayList<StopBox>();
        LngLat boxes[][] = getBoundingBox(distPerDay); 
        for(int i = 0; i<distPerDay; i++){
            List<BigStops> stops = stopHandler.getCuratedStopsInGeoP(boxes[i]);
            stopBoxes.add(new StopBox(stops, boxes[i]));
        }
        return stopBoxes;  
    }

    // This fucntion will return the bounding box of
    // This boudning box will be approximite 
    // @returns the first element is the lnglb, the second is the latlb, the third is the lngub, the fourth is the latub
    private LngLat[][] getBoundingBox(int distPerDay){

        final int speed = 27; // ~60 miles per hour in m/s

        final double maxSeconds = (0.125)*TripGeneratorUtils.convertDaysToSeconds(templateTrip.getDetails().getTripLength()); 
        final long delta = ((long)Math.floor(maxSeconds)) - minTripLen.getDurtaionS();
        
        final long height = (delta/2)*speed;  // this is the height over the horizontal in meters

        LngLat start = templateTrip.getTripData().getStartLocation(stopHandler).toLngLat(); 
        LngLat end = templateTrip.getTripData().getEndLocation(stopHandler).toLngLat();
        
        if(start.getLng() > end.getLng()) {
            LngLat temp = start; 
            start = end; 
            end = temp; 
        }

        final double angle = start.getBearing(end); 
        final double angleUp = angle + (Math.PI/2); 
        final double angleDown = angle - (Math.PI/2); 

        LngLat[][] boxList = new LngLat[distPerDay][4]; 

        for(int i = 0; i<distPerDay; i++){
            LngLat A = i == 0 ? start.getDest(height, angleUp) : boxList[i-1][0];
            LngLat B = i == 0 ? A.getDest(2*height, angleDown) : boxList[i-1][1];
            LngLat C = B.getDest(minTripLen.getDurationM()/distPerDay, angle);
            LngLat D = C.getDest(2*height, angleUp);
            LngLat box[] = {A,B,C,D};
            boxList[i] = box; 
        }

        return boxList; 

    }

    
}
