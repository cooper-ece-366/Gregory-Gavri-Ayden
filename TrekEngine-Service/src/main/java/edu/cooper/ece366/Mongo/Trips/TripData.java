// Written by Gregory Presser
package edu.cooper.ece366.Mongo.Trips;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import edu.cooper.ece366.Mongo.SerializingInterface;
import edu.cooper.ece366.Mongo.Stops.BigStops.BigStopHandler;
import edu.cooper.ece366.Mongo.Stops.BigStops.BigStops;
import edu.cooper.ece366.Mongo.Stops.SmallStops.SmallStopHandler;
import edu.cooper.ece366.Mongo.Stops.SmallStops.SmallStops;

public class TripData implements SerializingInterface {
    @BsonProperty("startLocation")
    private ObjectId startLocation;
    @BsonProperty("endLocation")
    private ObjectId endLocation;
    @BsonProperty("stops")
    private List<Stop> stops;

    @BsonCreator
    public TripData(
            @BsonProperty("startLocation") ObjectId startLocation,
            @BsonProperty("endLocation") ObjectId endLocation,
            @BsonProperty("stops") List<Stop> stops) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.stops = stops;
    }

    public TripData(JsonObject tripObj) {
        this.endLocation = new ObjectId(tripObj.get("endLocation").getAsString());
        this.startLocation = new ObjectId(tripObj.get("startLocation").getAsString());
        this.stops = new ArrayList<Stop>();
        tripObj.get("stops").getAsJsonArray().forEach(stop -> {
            this.stops.add(new Stop(stop.getAsJsonObject()));
        });
    }

    // Written By Gavri Kepets
    public TripData(boolean needsParsing) {
        this.endLocation = null;
        this.startLocation = null;
        this.stops = new ArrayList<Stop>();
    }

     public TripData(TripData t){
        this.startLocation = t.startLocation;
        this.endLocation = t.endLocation;
        this.stops = new ArrayList<Stop> ();
        t.stops.forEach(stop -> {
            this.stops.add(new Stop(stop));
        });
    }

    public ObjectId getStartLocation(){
        return startLocation; 
    }

    public BigStops getStartLocation(BigStopHandler handler){
        return handler.getById(startLocation);
    }

    public void setStartLocation(ObjectId startLocation){
        this.startLocation = startLocation; 
    }

    public ObjectId getEndLocation() {
        return endLocation;
     }

    public BigStops getEndLocation(BigStopHandler handler){
        return handler.getById(endLocation);
    }

    public void setEndLocation(ObjectId endLocation) {
        this.endLocation = endLocation;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }
              
    public void replaceStops(List<BigStops> stops){
        this.stops.clear(); 
        stops.forEach(stop -> {
            this.stops.add(new Stop(stop.getId(), new ArrayList<ObjectId>()));
        });
    }

    public List<BigStops> getBigStops(BigStopHandler handler){
        List<BigStops> bigStops = new ArrayList<BigStops>();
        for(Stop stop : stops){
            bigStops.add(handler.getById(stop.getBigStop()));
        }
        return bigStops;
    }

    @BsonIgnore
    public List<ObjectId> getBigStops(){
        List<ObjectId> bigStops = new ArrayList<ObjectId>();
        for(Stop stop : stops){
            bigStops.add(stop.getBigStop());
        }
        return bigStops;
    }

    public void addStop(ObjectId smallStop, ObjectId bigStop){
        for(int i = 0; i<stops.size(); i++){
            if(stops.get(i).getBigStop().equals(bigStop)){
                Stop temp = stops.get(i);
                temp.addStop(smallStop);
                stops.set(i, temp);
                return;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof TripData)) {
            return false;
        }
        TripData trip = (TripData) o;
        return startLocation.equals(trip.startLocation) &&
                endLocation.equals(trip.endLocation) &&
                stops.equals(trip.stops);
    }

    private class SerializedTripData implements SerializingInterface {


        private final String startLocation;
        private final String endLocation;
        private final List<Stop> stops;

        public SerializedTripData(TripData tripData) {
            this.startLocation = tripData.startLocation.toHexString();
            this.endLocation = tripData.endLocation.toHexString();
            this.stops = tripData.getStops();

        }
    }

    @Override
    public String toJSONString() {
        return new SerializedTripData(this).toJSONString();
    }

    @Override
    public String toJSONString(BigStopHandler bigStopHandler, SmallStopHandler smallStopHandler) {
        if (bigStopHandler == null || smallStopHandler == null)
            return this.toJSONString();
            SerializedTripData temp = new SerializedTripData(this);
        return temp.toJSONString(bigStopHandler, smallStopHandler);
    }

}
