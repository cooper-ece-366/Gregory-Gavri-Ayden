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

    public Score(double timeScore, Map<String,Double> tagScores, long timeDelta, List<Tag> idealTags, Map<String,Integer> tagCounts) {
        this.timeScore = timeScore;
        this.tagScores = tagScores;
        this.timeDelta = timeDelta;
        this.idealTags = idealTags;
        this.tagCounts = tagCounts;
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
        return sum/(tagScores.size() + 1); 
    }

    
}
