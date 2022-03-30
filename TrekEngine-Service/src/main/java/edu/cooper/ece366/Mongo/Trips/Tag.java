package edu.cooper.ece366.Mongo.Trips;

import com.google.gson.JsonObject;

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
    public Tag(JsonObject tagObj) {
        this.tag = tagObj.get("tag").getAsString();
        this.weight = tagObj.get("weight").getAsDouble();
    }
    public String getTag(){
        return tag; 
    }
    public Double getWeight(){
        return weight; 
    }

    @Override
    public boolean equals(Object o){
        if (o == this) return true;
        if (!(o instanceof Tag)) return false;
        Tag tag = (Tag) o;
        return this.tag.equals(tag.tag) && this.weight.equals(tag.weight);
    }
    
}
