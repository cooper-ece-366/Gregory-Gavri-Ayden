// Written By Gregory Presser
package edu.cooper.ece366.Mongo.Stops.SmallStops;

import java.util.Arrays;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import edu.cooper.ece366.Mongo.SerializingInterface;
import edu.cooper.ece366.Mongo.Stops.Loacation;
import edu.cooper.ece366.Mongo.Stops.BigStops.BigStopHandler;

public class SmallStops extends Loacation implements SerializingInterface{
     @BsonProperty("bigStop") private final ObjectId bigStop;

    @BsonCreator
    public SmallStops(
        @BsonProperty("_id") ObjectId id,
        @BsonProperty("name") String name,
        @BsonProperty("cords") List<Double> cords,
        @BsonProperty("type") String type,
        @BsonProperty("bigStop") ObjectId bigStop) {
            super(id,name,cords.get(1),cords.get(0),type); 
            this.bigStop = bigStop;
        }
    
    public SmallStops(
        ObjectId id,
        String name,
        Double lat,
        Double lng, 
        String type,
        ObjectId bigStop
    ){
        this(id,name,Arrays.asList(lng,lat),type,bigStop); 
    }

        private class SmallStopsSerilizer implements SerializingInterface {
            private final String id;
            private final String name; 
            private final Double lat;
            private final Double lng;
            private final String type;
        
    
            public SmallStopsSerilizer(SmallStops stops){
                this.id = stops.getId().toHexString(); 
                this.name = stops.getName(); 
                this.lat = stops.getLat(); 
                this.lng = stops.getLng(); 
                this.type = stops.getType(); 
            }
        }
    
        @Override
        public String toJSONString(){
            return new SmallStopsSerilizer(this).toJSONString(); 
        }
    
        @Override
        public String toJSONString(BigStopHandler bigStopHandler, SmallStopHandler smallstopHandler ){
            if (bigStopHandler == null || smallstopHandler == null)
                return this.toJSONString();
            return new SmallStopsSerilizer(this).toJSONString(bigStopHandler,smallstopHandler); 
        }

}
