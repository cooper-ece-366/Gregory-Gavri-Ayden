package edu.cooper.ece366.Utils.TripAI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.cooper.ece366.Exceptions.InvalidTripException;
import edu.cooper.ece366.Mongo.Stops.BigStops.BigStopHandler;
import edu.cooper.ece366.Mongo.Stops.BigStops.BigStops;
import edu.cooper.ece366.Mongo.Trips.Tag;
import edu.cooper.ece366.Mongo.Trips.Trip;
import edu.cooper.ece366.Utils.GeoLocation.DirData;
import edu.cooper.ece366.Utils.GeoLocation.GeoLocationHandler;
import edu.cooper.ece366.Utils.GeoLocation.LngLat;


public class TripGenerator {

    private BigStopHandler stopHandler;
    private static GeoLocationHandler geoHandler = new GeoLocationHandler();

    private DirData minTripLen; 
    private List<StopBox> stops; 
    private Trip templateTrip; 
    private Score currentScore; 

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


    public TripGenerator(Trip templateTrip,BigStopHandler stopHandler, int distPerDay) throws IOException{
        this.templateTrip = templateTrip;
        this.stopHandler = stopHandler;
        this.minTripLen = calculateMinTripLen(); 
        this.stops = makeStops(distPerDay); 
        this.currentScore = this.calculateScore(); 
    }

    public DirData getMinTripLen() {
        return minTripLen;
    }

    public Trip generateTrip(){
        return new Generator(stops).generate(); 
    }

    private Score calculateScore() throws IOException{
        ArrayList<String> stopStr = new ArrayList<String>();
        Map<String,Integer> tags = new HashMap<String,Integer>(); 
        int stopCount = 0; 
        for(StopBox box: stops){
            stopCount += box.stops.size(); 
            for(BigStops stop: box.stops){
                stopStr.add(stop.toLngLat().getDirStr());
                tags.put(stop.getType(), tags.getOrDefault(stop.getType(), 0) + 1); 
            }
        }

        final long timeData = geoHandler.directions(stopStr).getDurtaionS();
        final double timeScore = (timeData - TripGeneratorUtils.convertDaysToSeconds(templateTrip.getDetails().getTripLength())) / ((double)(TripGeneratorUtils.convertDaysToSeconds(templateTrip.getDetails().getTripLength())));
        Map<String,Double> tagScores = new HashMap<String,Double>();
        for (Tag tag: templateTrip.getDetails().getTags()){
            tagScores.put(
                tag.getTag(),
                tags.getOrDefault(tag.getTag(), 0) - (tag.getWeight()*stopCount) / (double)stopCount
            );
        }

        return new Score(timeScore,tagScores); 
    }

    private DirData calculateMinTripLen(){

        ArrayList<String> list = new ArrayList<String>(){{
            BigStops start = stopHandler.getById(templateTrip.getTripData().getStartLocation()); 
            BigStops end = stopHandler.getById(templateTrip.getTripData().getEndLocation()); 
            add(start.toLngLat().getDirStr());
            add(end.toLngLat().getDirStr()); 
        }};
        try {
            return geoHandler.directions(list);  
        } catch (IOException e) {
            e.printStackTrace();
            throw new InvalidTripException(); 
        }
    }



    private ArrayList<StopBox> makeStops(int distPerDay){
        ArrayList<StopBox> stopBoxes = new ArrayList<StopBox>();
        LngLat boxes[][] = getBoundingBox(distPerDay); 
        for(int i = 0; i<distPerDay; i++){
            List<BigStops> stopsB = stopHandler.getCuratedRandomStopsInGeoPWR(
                boxes[i],
                10,
                templateTrip.getTripData().getBigStops()
            );
            Collections.sort(
                stopsB,
                new SortBigStops(templateTrip.getTripData().getStartLocation(stopHandler)).getSort()
            );
            stopBoxes.add(new StopBox(stopsB, boxes[i]));
        }
        return stopBoxes;  
    }

    public List<StopBox> getStops() {
        return stops; 
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
        
        if(start.getLng() < end.getLng()) {
            LngLat temp = start; 
            start = end; 
            end = temp; 
        }

        final double angle = start.getBearing(end); 
        final double angleUp = angle + (Math.PI/2); 
        final double angleDown = angle - (Math.PI/2); 

        LngLat startUp = start.getDest(height, angleUp);
        LngLat startDown = start.getDest(height, angleDown);
        LngLat endUp = end.getDest(height, angleUp);
        LngLat endDown = end.getDest(height, angleDown);

        LngLat[][] boxList = new LngLat[distPerDay][6]; 

        for(int i = 0; i<distPerDay; i++){
            LngLat A = i == 0 ? start : boxList[i-1][3];
            LngLat B = i == 0 ? startUp : boxList[i-1][2];
            LngLat C = startUp.getIntermidiatePoint(endUp, minTripLen.getDurationM(), (i+1)/((double)distPerDay));
            LngLat D = start.getIntermidiatePoint(end, minTripLen.getDurationM(), (i+1)/((double)distPerDay));
            LngLat E = startDown.getIntermidiatePoint(endDown, minTripLen.getDurationM(), (i+1)/((double)distPerDay));
            LngLat F = i == 0 ? startDown : boxList[i-1][4];
            LngLat box[] = {A,B,C,D,E,F};
            boxList[i] = box; 
        }

        return boxList; 

    }

    
}
