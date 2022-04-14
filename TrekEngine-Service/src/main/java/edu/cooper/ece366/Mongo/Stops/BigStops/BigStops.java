package edu.cooper.ece366.Mongo.Stops.BigStops;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import edu.cooper.ece366.Mongo.Stops.Loacation;

public class BigStops extends Loacation {

    @BsonProperty("isCurated") private final Boolean isCurated; 

    @BsonCreator
    public BigStops(
        @BsonProperty("_id") ObjectId id,
        @BsonProperty("name") String name,
        @BsonProperty("lat") Double lat,
        @BsonProperty("lng") Double lng,
        @BsonProperty("type") String type,
        @BsonProperty("isCurated") Boolean isCurated) {
            super(id,name,lat,lng,type); 
            this.isCurated = isCurated; 
    }

    public Boolean getIsCurated() {
        return isCurated;
    }

}
