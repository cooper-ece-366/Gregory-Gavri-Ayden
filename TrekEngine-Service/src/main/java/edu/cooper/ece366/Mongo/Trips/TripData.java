package edu.cooper.ece366.Mongo.Trips;

import java.util.ArrayList;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import edu.cooper.ece366.Mongo.SerializingInterface;

public class TripData implements SerializingInterface{
    @BsonProperty("startLocation") private final Loacation startLocation;
    @BsonProperty("endLocation") private final Loacation endLocation;
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
    public Loacation getStartLocation(){
        return startLocation; 
    }
    public Loacation getEndLocation(){
        return endLocation; 
    }
    public List<Loacation> getStops(){
        return stops; 
    }
    
}
