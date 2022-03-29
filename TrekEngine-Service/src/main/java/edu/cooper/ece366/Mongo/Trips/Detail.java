package edu.cooper.ece366.Mongo.Trips;

import java.util.Date;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import edu.cooper.ece366.Mongo.SerializingInterface;

public class Detail implements SerializingInterface {
    @BsonProperty("startDate") private final Date startDate;
    @BsonProperty("tripLength") private final Integer tripLength;
    @BsonProperty("tags") private final List<Tag> tags;
    
    @BsonCreator
    public Detail(
        @BsonProperty("startDate") Date startDate,
        @BsonProperty("tripLength") Integer tripLength, 
        @BsonProperty("tags") List<Tag> tags) {
        this.startDate = startDate;
        this.tripLength = tripLength;
        this.tags = tags;
    }
    public Date getStartDate(){
        return startDate; 
    }
    public Integer getTripLength(){
        return tripLength; 
    }
    public List<Tag> getTags(){
        return tags; 
    }

    
}
