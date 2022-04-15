package edu.cooper.ece366.Mongo.Stops;

import com.google.gson.JsonObject;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import edu.cooper.ece366.Mongo.IDInterface;
import edu.cooper.ece366.Mongo.SerializingInterface;

public abstract class Loacation implements SerializingInterface, IDInterface{
    @BsonProperty("_id") protected final ObjectId id;
    @BsonProperty("name") protected final String name; 
    @BsonProperty("lat") protected final Double lat;
    @BsonProperty("lng") protected final Double lng;
    @BsonProperty("type") protected final String type;

    public Loacation(ObjectId id,String name,Double lat,Double lng,String type){
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
    
    @Override
    public ObjectId getId(){
        return this.id;
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
