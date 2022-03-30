package edu.cooper.ece366.Mongo.Trips;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import edu.cooper.ece366.Mongo.SerializingInterface;
public class Trip implements SerializingInterface { 
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


    public Trip(JsonObject tripJson){
        this.id = new ObjectId(); 
        this.meta = new Meta(tripJson.get("meta").getAsJsonObject());
        this.tripData = new TripData(tripJson.get("trip").getAsJsonObject());
        this.details = new Detail(tripJson.get("details").getAsJsonObject());
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

    @Override
    public boolean equals(Object o){
        if (o == this) return true;
        if (!(o instanceof Trip)) {
            return false;
        }
        Trip other = (Trip) o;
        
        return this.id.equals(other.getId()) && this.meta.equals(other.getMeta()) && this.tripData.equals(other.getTripData()) && this.details.equals(other.getDetails());
    }

    private class SerializedTrip implements SerializingInterface{
        
        private String _id; 
        private Meta meta;
        private TripData tripData;
        private Detail details;

        public SerializedTrip(Trip trip){
            this._id = trip.getId().toHexString();
            this.meta = trip.getMeta();
            this.tripData = trip.getTripData();
            this.details = trip.getDetails();
        }
    }

    @Override
    public String toJSONString() {
        return new Gson().toJson(new SerializedTrip(this));
    }
    

}
