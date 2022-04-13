package edu.cooper.ece366.Mongo.Trips;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import edu.cooper.ece366.Mongo.SerializingInterface;

public class TripData implements SerializingInterface{
    @BsonProperty("startLocation") private ObjectId startLocation;
    @BsonProperty("endLocation") private ObjectId endLocation;
    @BsonProperty("stops") private final List<ObjectId> stops;

    @BsonCreator
    public TripData(
        @BsonProperty("startLocation") ObjectId startLocation, 
        @BsonProperty("endLocation") ObjectId endLocation, 
        @BsonProperty("stops") List<ObjectId> stops) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.stops = stops;
    }

    public TripData(JsonObject tripObj){
        this.endLocation = new ObjectId(tripObj.get("endLocation").getAsString());
        this.startLocation = new ObjectId(tripObj.get("startLocation").getAsString());
        this.stops = new ArrayList<ObjectId> (); 
        tripObj.get("stops").getAsJsonArray().forEach(stop -> {
            this.stops.add(new ObjectId(stop.getAsString()));
        });
    }

    public ObjectId getStartLocation(){
        return startLocation; 
    }
    public void setStartLocation(ObjectId startLocation){
        this.startLocation = startLocation; 
    }
    public ObjectId getEndLocation(){
        return endLocation; 
    }
    public void setEndLocation(ObjectId endLocation){
        this.endLocation = endLocation; 
    }
    public List<ObjectId> getStops(){
        return stops; 
    }

    public void addStop(ObjectId stop, int index){
        this.stops.add(index, stop);
    }

    public void addStop(ObjectId stop){
        this.stops.add(stop);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof TripData)) {
            return false;
        }
        TripData trip = (TripData) o;
        return startLocation.equals(trip.startLocation) &&
               endLocation.equals(trip.endLocation) &&
               stops.equals(trip.stops);
    }
    
}
