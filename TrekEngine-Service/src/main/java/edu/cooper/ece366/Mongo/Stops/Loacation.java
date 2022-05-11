// Written By Gregory Presser
package edu.cooper.ece366.Mongo.Stops;

import com.google.gson.JsonObject;

import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List; 

import edu.cooper.ece366.Mongo.IDInterface;
import edu.cooper.ece366.Mongo.SerializingInterface;
import edu.cooper.ece366.Utils.GeoLocation.LngLat;

public abstract class Loacation implements SerializingInterface, IDInterface{
    @BsonProperty("_id") protected final ObjectId id;
    @BsonProperty("name") protected final String name; 
    @BsonProperty("cords") protected final List<Double> cords; 
    @BsonProperty("type") protected final String type;

    public Loacation(ObjectId id,String name,Double lat,Double lng,String type){
            this.cords = new ArrayList<Double>(){{
                add(lng); 
                add(lat); 
            }}; 
            this.id = id;
            this.name = name;
            this.type = type;
        }

    public Loacation(JsonObject locObj) {
        this.cords = new ArrayList<Double>(){{
            add(locObj.get("lng").getAsDouble());
            add(locObj.get("lat").getAsDouble());
        }};
        this.id = new ObjectId(locObj.get("_id").getAsString());
        this.name = locObj.get("name").getAsString();
        this.type = locObj.get("type").getAsString();
    }
    
    @Override
    public ObjectId getId(){
        return this.id;
    }

    public String getName(){
        return name; 
    }

    public List<Double> getCords(){
        return cords; 
    }

    @BsonIgnore
    public Double getLat(){
        return cords.get(1); 
    }
    @BsonIgnore
    public Double getLng(){
        return cords.get(0); 
    }
    public String getType(){
        return type; 
    }

    public LngLat toLngLat(){
        return new LngLat(this); 
    }

    @Override
    public boolean equals(Object o){
        if (o == this) return true;
        if (!(o instanceof Loacation)) return false;
        Loacation loc = (Loacation) o;
        return this.name.equals(loc.name) && this.cords.equals(loc.cords) && this.type.equals(loc.type);
    }
    
}
