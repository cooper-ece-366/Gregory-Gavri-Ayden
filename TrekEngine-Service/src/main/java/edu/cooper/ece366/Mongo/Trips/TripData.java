package edu.cooper.ece366.Mongo.Trips;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import edu.cooper.ece366.Mongo.SerializingInterface;

public class TripData implements SerializingInterface{
    @BsonProperty("startLocation") private Loacation startLocation;
    @BsonProperty("endLocation") private Loacation endLocation;
    @BsonProperty("stops") private final List<Loacation> stops;

    @BsonCreator
    public TripData(
        @BsonProperty("startLocation") Loacation startLocation, 
        @BsonProperty("endLocation") Loacation endLocation, 
        @BsonProperty("stops") List<Loacation> stops) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.stops = stops;
    }

    public TripData(JsonObject tripObj){
        this.endLocation = new Loacation(tripObj.get("endLocation").getAsJsonObject());
        this.startLocation = new Loacation(tripObj.get("startLocation").getAsJsonObject());
        this.stops = new ArrayList<Loacation> (); 
        tripObj.get("stops").getAsJsonArray().forEach(stop -> {
            this.stops.add(new Loacation(stop.getAsJsonObject()));
        });
    }

    public Loacation getStartLocation(){
        return startLocation; 
    }
    public void setStartLocation(Loacation startLocation){
        this.startLocation = startLocation; 
    }
    public Loacation getEndLocation(){
        return endLocation; 
    }
    public void setEndLocation(Loacation endLocation){
        this.endLocation = endLocation; 
    }
    public List<Loacation> getStops(){
        return stops; 
    }

    public void addStop(Loacation stop, int index){
        this.stops.add(index, stop);
    }

    public void addStop(Loacation stop){
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
