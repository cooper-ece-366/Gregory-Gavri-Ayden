package edu.cooper.ece366.Mongo.Trips;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import edu.cooper.ece366.Mongo.SerializingInterface;

public class Tag implements SerializingInterface{
    @BsonProperty("tag") private final String tag;
    @BsonProperty("weight") private final Double weight;

    @BsonCreator
    public Tag(
        @BsonProperty("tag") String tag, 
        @BsonProperty("weight") Double weight) {
        this.tag = tag;
        this.weight = weight;
    }
    public String getTag(){
        return tag; 
    }
    public Double getWeight(){
        return weight; 
    }
    
}
