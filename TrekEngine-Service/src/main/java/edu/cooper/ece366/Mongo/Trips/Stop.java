// Written By Gregory Presser
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

    @BsonProperty("bigStop")
    private ObjectId bigStop;
    @BsonProperty("smallStops")
    private final List<ObjectId> smallStops;

    @BsonCreator
    public Stop(
            @BsonProperty("bigStop") ObjectId bigStop,
            @BsonProperty("smallStops") List<ObjectId> smallStops) {
        this.bigStop = bigStop;
        this.smallStops = smallStops;
    }

    public Stop(JsonObject obj) {
        if (obj.get("bigStop").isJsonObject()) {
            this.bigStop = new ObjectId(obj.get("bigStop").getAsJsonObject().get("id").getAsString());
        } else {
            this.bigStop = new ObjectId(obj.get("bigStop").getAsString());
        }
        this.smallStops = new ArrayList<ObjectId>();
        obj.get("smallStops").getAsJsonArray().forEach(stop -> {
            if (stop.isJsonObject()) {
                this.smallStops.add(new ObjectId(stop.getAsJsonObject().get("id").getAsString()));
            } else {
                this.smallStops.add(new ObjectId(stop.getAsString()));
            }
        });
    }

    public Stop(ObjectId id) {
        this.bigStop = id;
        this.smallStops = new ArrayList<ObjectId>();
    }

    public Stop(Stop s) {
        this.bigStop = s.bigStop;
        this.smallStops = new ArrayList<ObjectId>();
        s.smallStops.forEach(stop -> {
            this.smallStops.add(stop);
        });
    }

    public ObjectId getBigStop() {
        return bigStop;
    }

    public void setBigStop(ObjectId bigStop) {
        this.bigStop = bigStop;
    }

    public BigStops getBigStop(BigStopHandler handler) {
        return handler.getById(bigStop);
    }

    public ArrayList<SmallStops> getSmallStops(SmallStopHandler handler) {
        return handler.rawQuery(Filters.in("_id", smallStops));
    }

    public List<ObjectId> getSmallStops() {
        return smallStops;
    }

    public void addStop(ObjectId smallStop) {
        this.smallStops.add(smallStop);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Stop))
            return false;
        Stop stop = (Stop) o;
        return this.bigStop.equals(stop.bigStop) && this.smallStops.equals(stop.smallStops);
    }

    private class SerializedStop implements SerializingInterface {
        private final String bigStop;
        private final List<String> smallStops;

        public SerializedStop(Stop stop) {
            this.bigStop = stop.getBigStop().toHexString();
            this.smallStops = new ArrayList<String>();
            for (ObjectId id : stop.getSmallStops()) {
                smallStops.add(id.toHexString());
            }
        }
    }

    private class SerializedStopExtended implements SerializingInterface {
        private final BigStops bigStop;
        private final List<SmallStops> smallStops;

        public SerializedStopExtended(Stop stop, BigStopHandler bigStopHandler, SmallStopHandler smallStopHandler) {

            this.bigStop = bigStopHandler.getById(stop.getBigStop());
            this.smallStops = new ArrayList<SmallStops>();
            for (ObjectId id : stop.getSmallStops()) {
                smallStops.add(smallStopHandler.getById(id));
            }
        }
    }

    @Override
    public String toJSONString() {
        return new SerializedStop(this).toJSONString();
    }

    @Override
    public String toJSONString(BigStopHandler bigStopHandler, SmallStopHandler smallStopHandler) {
        if (bigStopHandler == null || smallStopHandler == null)
            return this.toJSONString();
        return new SerializedStopExtended(this, bigStopHandler, smallStopHandler).toJSONString();
    }

}
