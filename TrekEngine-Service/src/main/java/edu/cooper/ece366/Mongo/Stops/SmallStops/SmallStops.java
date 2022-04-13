package edu.cooper.ece366.Mongo.Stops.SmallStops;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import edu.cooper.ece366.Mongo.Stops.Loacation;

public class SmallStops extends Loacation {
     @BsonProperty("bigStop") private final ObjectId bigStop;

    @BsonCreator
    public SmallStops(
        @BsonProperty("_id") ObjectId id,
        @BsonProperty("name") String name,
        @BsonProperty("lat") Double lat,
        @BsonProperty("lng") Double lng,
        @BsonProperty("type") String type,
        @BsonProperty("bigStop") ObjectId bigStop) {
            super(id,name,lat,lng,type); 
            this.bigStop = bigStop;
        }

}
