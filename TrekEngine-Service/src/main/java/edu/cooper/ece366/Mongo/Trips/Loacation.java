package edu.cooper.ece366.Mongo.Trips;

import com.google.gson.JsonObject;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import edu.cooper.ece366.Mongo.SerializingInterface;

public class Loacation implements SerializingInterface{
    @BsonProperty("_id") private final ObjectId id;
    @BsonProperty("name") private final String name; 
    @BsonProperty("lat") private final Double lat;
    @BsonProperty("lng") private final Double lng;
    @BsonProperty("type") private final String type;

    @BsonCreator
    public Loacation(
        @BsonProperty("_id") ObjectId id,
        @BsonProperty("name") String name,
        @BsonProperty("lat") Double lat,
        @BsonProperty("lng") Double lng,
        @BsonProperty("type") String type){
            this.id = id;
            this.name = name;
            this.lat = lat;
            this.lng = lng;
            this.type = type;
        }

    public Loacation(JsonObject locObj) {
        this.id = new ObjectId(locObj.get("_id").getAsString());
        this.name = locObj.get("name").getAsString();
        this.lat = locObj.get("lat").getAsDouble();
        this.lng = locObj.get("lng").getAsDouble();
        this.type = locObj.get("type").getAsString();
        
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

    @Override
    public boolean equals(Object o){
        if (o == this) return true;
        if (!(o instanceof Loacation)) return false;
        Loacation loc = (Loacation) o;
        return this.name.equals(loc.name) && this.lat.equals(loc.lat) && this.lng.equals(loc.lng) && this.type.equals(loc.type);
    }
    
}
