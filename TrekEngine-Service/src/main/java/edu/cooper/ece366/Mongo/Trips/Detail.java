package edu.cooper.ece366.Mongo.Trips;

import java.util.ArrayList;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import edu.cooper.ece366.Mongo.SerializingInterface;

public class Detail implements SerializingInterface {
    @BsonProperty("startDate") private final String startDate;
    @BsonProperty("tripLength") private final Integer tripLength;
    @BsonProperty("tags") private final ArrayList<Tag> tags;
    
    @BsonCreator
    public Detail(
        @BsonProperty("startDate") String startDate,
        @BsonProperty("tripLength") Integer tripLength, 
        @BsonProperty("tags") ArrayList<Tag> tags) {
        this.startDate = startDate;
        this.tripLength = tripLength;
        this.tags = tags;
    }
    public String getStartDate(){
        return startDate; 
    }
    public Integer getTripLength(){
        return tripLength; 
    }
    public ArrayList<Tag> getTags(){
        return tags; 
    }

    
}
