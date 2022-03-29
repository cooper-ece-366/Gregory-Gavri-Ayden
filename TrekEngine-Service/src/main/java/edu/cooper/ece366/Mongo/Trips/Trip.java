package edu.cooper.ece366.Mongo.Trips;

import com.google.gson.Gson;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
public class Trip { 
    @BsonProperty("_id") private final ObjectId id;
    @BsonProperty("meta") private final Meta meta;
    @BsonProperty("trip") private final TripData tripData;
    @BsonProperty("details") private final Detail details; 

    @BsonCreator
    public Trip(
        @BsonProperty("_id") ObjectId id,
        @BsonProperty("meta") Meta meta,
        @BsonProperty("trip") TripData tripData,
        @BsonProperty("details") Detail details
        ){
        this.id = id; 
        this.meta = meta;
        this.tripData = tripData;
        this.details = details;
    }
    public Meta getMeta(){
        return meta; 
    }
    public ObjectId getId(){
        return id; 
    }
    public TripData getTripData(){
        return tripData; 
    }
    public Detail getDetails(){
        return details; 
    }
    public String toJSONString() {
        return new Gson().toJson(this);
    }

}
