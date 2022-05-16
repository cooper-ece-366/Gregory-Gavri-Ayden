// Written By Gregory Presser
package edu.cooper.ece366.Utils.TripAI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.cooper.ece366.Mongo.Stops.BigStops.BigStops;
import edu.cooper.ece366.Mongo.Trips.Tag;

public class TripGeneratorUtils {

    public static long convertDaysToSeconds(int days) {
        return days * 86400; // 24 * 60 * 60
    }

    public static List<BigStops> getStops(List<StopBox> boxes){
        List<BigStops> stops = new ArrayList<BigStops>();
        for(StopBox box : boxes){
            stops.addAll(box.stops);
        }
        return stops;        
    }


    public static Score calculateScore(long timeData, int tripLen, List<Tag> templateTags, Map<String,Integer> tags, int stopCount) {
        // final long timeData = geoHandler.directions(stopStr).getDurtaionS();
        final double timeScore = calucuateTimeScore(timeData,tripLen);
        Map<String,Double> tagScores = calculasteTagScore(templateTags,stopCount,tags);

        return new Score(timeScore,tagScores, timeData, templateTags, tags); 
    }

    private static double calucuateTimeScore(long timeData, int tripLen ){
        long lenS = convertDaysToSeconds(tripLen); 
        return  ((double)(timeData) / (double)lenS);
    }

    private static Map<String,Double> calculasteTagScore(List<Tag> templateTags, int stopCount, Map<String,Integer> tags){
        Map<String,Double> tagScores = new HashMap<String,Double>();
        for (Tag tag: templateTags){
            tagScores.put(
                tag.getTag(),
                (tags.getOrDefault(tag.getTag(), 0) / (double)stopCount) / tag.getWeight()
            );
        }
        return tagScores;
    }

    // returns a new score or null if it is not to be removed 
    public static Score estimateScore(Score prevScore, List<BigStops> stops, int rIndex, int tripLen, BigStops prevBox, BigStops nextBox) {

        // can't remove the desination stop or the origin stop
        if(stops.size() <= 1){
            return null;
        }
        
        // to get the time delta instead of doing an API call we are going to caculate the time difference 
        // as crow flies between the stops assuming a driving speed of 60 mph = 27 m/s

        BigStops prevStop = rIndex == 0 ? prevBox : stops.get(rIndex - 1);
        BigStops currStop = stops.get(rIndex);
        BigStops nextStop = rIndex == stops.size() -1 ? nextBox: stops.get(rIndex + 1);

        final double initalDelta = prevStop.toLngLat().deltaAsMeters(currStop.toLngLat()) + 
                             currStop.toLngLat().deltaAsMeters(nextStop.toLngLat());
        

        // remove the current stop
        final double woDelta = prevStop.toLngLat().deltaAsMeters(nextStop.toLngLat());

        final double timeDelta = (initalDelta- woDelta) / 27.0; 
        final long timeData = prevScore.getTimeDelta() - ((long)Math.round(timeDelta));

        final double timeScore = calucuateTimeScore(timeData,tripLen);

        Map<String,Integer> tagCounts = prevScore.getTagCounts(); 

        tagCounts.put(currStop.getType(), tagCounts.get(currStop.getType()) - 1);
        Map<String,Double> tagScores = calculasteTagScore(prevScore.getIdealTags(),prevScore.getStopCount()-1,tagCounts);

        Score newScore = new Score(timeScore,tagScores, timeData, prevScore.getIdealTags(), tagCounts,prevScore.getStopCount()-1);
        final double newScoreA = newScore.getAvgScore();
        final double prevScoreA = prevScore.getAvgScore();

        if( Math.abs(newScoreA - 1) < Math.abs(prevScoreA - 1)){
            return newScore;
        }

        return null; 

    }
    
}
