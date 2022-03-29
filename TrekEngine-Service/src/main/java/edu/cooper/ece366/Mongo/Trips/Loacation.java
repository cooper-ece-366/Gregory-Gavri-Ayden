package edu.cooper.ece366.Mongo.Trips;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import edu.cooper.ece366.Mongo.SerializingInterface;

public class Loacation implements SerializingInterface{
    @BsonProperty("name") private final String name; 
    @BsonProperty("lat") private final Double lat;
    @BsonProperty("lng") private final Double lng;
    @BsonProperty("type") private final String type;

    @BsonCreator
    public Loacation(
        @BsonProperty("name") String name,
        @BsonProperty("lat") Double lat,
        @BsonProperty("lng") Double lng,
        @BsonProperty("type") String type){
            this.name = name;
            this.lat = lat;
            this.lng = lng;
            this.type = type;
        }

    public String getName(){
        return name; 
    }
    public Double getLat(){
        return lat; 
    }
    public Double getLng(){
        return lng; 
    }
    public String getType(){
        return type; 
    }
    
}
