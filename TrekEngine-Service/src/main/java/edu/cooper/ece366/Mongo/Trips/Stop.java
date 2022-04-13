package edu.cooper.ece366.Mongo.Trips;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.mongodb.client.model.Filters;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import edu.cooper.ece366.Mongo.SerializingInterface;
import edu.cooper.ece366.Mongo.Stops.BigStops.BigStopHandler;
import edu.cooper.ece366.Mongo.Stops.BigStops.BigStops;
import edu.cooper.ece366.Mongo.Stops.SmallStops.SmallStopHandler;
import edu.cooper.ece366.Mongo.Stops.SmallStops.SmallStops;

public class Stop implements SerializingInterface {


    @BsonProperty("bigStop") private final ObjectId bigStop; 
    @BsonProperty("smallStops") private final List<ObjectId> smallStops;

    @BsonCreator
    public Stop (
        @BsonProperty("bigStop") ObjectId bigStop,
        @BsonProperty("smallStops") List<ObjectId> smallStops) {
        this.bigStop = bigStop;
        this.smallStops = smallStops;
    }

    public Stop (JsonObject obj){
        this.bigStop = new ObjectId(obj.get("bigStop").getAsString());
        this.smallStops = new ArrayList<ObjectId> (); 
        obj.get("smallStops").getAsJsonArray().forEach(stop -> {
            this.smallStops.add(new ObjectId(stop.getAsString()));
        });
    }

    public ObjectId getBigStop() {
        return bigStop;
    }

    public BigStops getBigStop(BigStopHandler handler){
        return handler.getById(bigStop);
    }

    public ArrayList<SmallStops> getSmallStops(SmallStopHandler handler) {
        return handler.rawQuery(Filters.in("_id", smallStops)); 
    }

    public List<ObjectId> getSmallStops() {
        return smallStops;
    }
    
}