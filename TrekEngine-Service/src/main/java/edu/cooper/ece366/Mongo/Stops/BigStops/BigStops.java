package edu.cooper.ece366.Mongo.Stops.BigStops;

import java.util.ArrayList;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import edu.cooper.ece366.Mongo.SerializingInterface;
import edu.cooper.ece366.Mongo.Stops.Loacation;
import edu.cooper.ece366.Mongo.Stops.SmallStops.SmallStopHandler;

public class BigStops extends Loacation implements SerializingInterface {

    @BsonProperty("isCurated") private final Boolean isCurated; 

    @BsonCreator
    public BigStops(
        @BsonProperty("_id") ObjectId id,
        @BsonProperty("name") String name,
        @BsonProperty("cords") List<Double> cords,
        @BsonProperty("type") String type,
        @BsonProperty("isCurated") Boolean isCurated) {
            super(id,name,cords.get(1),cords.get(0),type); 
            this.isCurated = isCurated; 
    }

    public BigStops(
        ObjectId id,
        String name,
        Double lat,
        Double lng, 
        String type,
        Boolean isCurated
    ){
        this(id,name,new ArrayList<Double>(){{add(lng);add(lat);}},type,isCurated); 
    }

    public Boolean getIsCurated() {
        return isCurated;
    }

    private class BigStopsSerilizer implements SerializingInterface {
        private final String id;
        private final String name; 
        private final Double lat;
        private final Double lng;
        private final String type;
        private final Boolean isCurated; 

        public BigStopsSerilizer(BigStops stops){
            this.id = stops.getId().toHexString(); 
            this.name = stops.getName(); 
            this.lat = stops.getLat(); 
            this.lng = stops.getLng(); 
            this.type = stops.getType(); 
            this.isCurated = stops.getIsCurated();
        }
    }

    @Override
    public String toJSONString(){
        return new BigStopsSerilizer(this).toJSONString(); 
    }

    @Override
    public String toJSONString(BigStopHandler bigStopHandler, SmallStopHandler smallstopHandler ){
        if (bigStopHandler == null || smallstopHandler == null)
            return this.toJSONString();
        return new BigStopsSerilizer(this).toJSONString(bigStopHandler,smallstopHandler); 
    }

}
