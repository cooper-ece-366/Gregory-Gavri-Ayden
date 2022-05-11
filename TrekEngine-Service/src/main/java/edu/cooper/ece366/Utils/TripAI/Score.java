// Written By Gregory Presser
package edu.cooper.ece366.Utils.TripAI;

import java.util.List;
import java.util.Map;

import edu.cooper.ece366.Mongo.Trips.Tag;

public class Score {
    private double timeScore; 
    private Map<String,Double> tagScores; 
    private long timeDelta; 
    private List<Tag> idealTags; 
    private Map<String,Integer> tagCounts; 
    private int stopCount; 

    public Score(double timeScore, Map<String,Double> tagScores, long timeDelta, List<Tag> idealTags, Map<String,Integer> tagCounts) {
        // this.timeScore = timeScore;
        // this.tagScores = tagScores;
        // this.timeDelta = timeDelta;
        // this.idealTags = idealTags;
        // this.tagCounts = tagCounts;
        this(timeScore, tagScores,  timeDelta,  idealTags, tagCounts,  0);
        for(Integer i : tagCounts.values()){
            this.stopCount += i;
        }
    }


    public Score(double timeScore, Map<String,Double> tagScores, long timeDelta, List<Tag> idealTags, Map<String,Integer> tagCounts, int stopCount) {
        this.timeScore = timeScore;
        this.tagScores = tagScores;
        this.timeDelta = timeDelta;
        this.idealTags = idealTags;
        this.tagCounts = tagCounts;
        this.stopCount = stopCount; 
    }

    public double getTimeScore() {
        return timeScore;
    }
    public Map<String,Double> getTagScores() {
        return tagScores;
    }

    public long getTimeDelta() {
        return timeDelta;
    }
    public Map<String,Integer> getTagCounts() {
        return tagCounts;
    }

    public List<Tag> getIdealTags() {
        return idealTags;
    }

    public double getAvgScore(){
        double sum = timeScore; 
        for(Map.Entry<String,Double> entry : tagScores.entrySet()){
            sum += entry.getValue();
        }      
        return sum/((double)(tagScores.size() + 1)); 
    }

    public int getStopCount() {
        return stopCount;
    }

    
}
