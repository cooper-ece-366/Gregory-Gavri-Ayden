package edu.cooper.ece366.Mongo.Stops.BigStops;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import edu.cooper.ece366.Mongo.SerializingInterface;
import edu.cooper.ece366.Mongo.Stops.Loacation;
import edu.cooper.ece366.Mongo.Stops.SmallStops.SmallStopHandler;

public class BigStops extends Loacation implements SerializingInterface {

    @BsonProperty("isCurated")
    private final Boolean isCurated;

    @BsonCreator
    public BigStops(
            @BsonProperty("_id") ObjectId id,
            @BsonProperty("name") String name,
            @BsonProperty("lat") Double lat,
            @BsonProperty("lng") Double lng,
            @BsonProperty("type") String type,
            @BsonProperty("isCurated") Boolean isCurated) {
        super(id, name, lat, lng, type);
        this.isCurated = isCurated;
    }

    public Boolean getIsCurated() {
        return isCurated;
    }

    public ObjectId getId() {
        return id;
    }

    private class BigStopsSerilizer implements SerializingInterface {
        private final String id;
        private final String name;
        private final Double lat;
        private final Double lng;
        private final String type;
        private final Boolean isCurated;

        public BigStopsSerilizer(BigStops stops) {
            this.id = stops.getId().toHexString();
            this.name = stops.getName();
            this.lat = stops.getLat();
            this.lng = stops.getLng();
            this.type = stops.getType();
            this.isCurated = stops.getIsCurated();
        }
    }

    @Override
    public String toJSONString() {
        return new BigStopsSerilizer(this).toJSONString();
    }

    @Override
    public String toJSONString(BigStopHandler bigStopHandler, SmallStopHandler smallstopHandler) {
        if (bigStopHandler == null || smallstopHandler == null)
            return this.toJSONString();
        return new BigStopsSerilizer(this).toJSONString(bigStopHandler, smallstopHandler);
    }

}
