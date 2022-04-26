package edu.cooper.ece366.Utils.TripAI;

import java.util.Map;

public class Score {
    private double timeScore; 
    private Map<String,Double> tagScores; 

    public Score(double timeScore, Map<String,Double> tagScores) {
        this.timeScore = timeScore;
        this.tagScores = tagScores;
    }

    public double getTimeScore() {
        return timeScore;
    }
    public Map<String,Double> getTagScores() {
        return tagScores;
    }

    private double getAvgScore(){
        double sum = timeScore; 
        for(Map.Entry<String,Double> entry : tagScores.entrySet()){
            sum += entry.getValue();
        }      
        return sum/(tagScores.size() + 1); 
    }

    
}
