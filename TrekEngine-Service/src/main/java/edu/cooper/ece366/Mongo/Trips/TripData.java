package edu.cooper.ece366.Mongo.Trips;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import org.bson.codecs.pojo.annotations.BsonCreator;
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

    public TripData(boolean needsParsing) {
        this.endLocation = null;
        this.startLocation = null;
        this.stops = new ArrayList<Stop>();
    }

    public ObjectId getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(ObjectId startLocation) {
        this.startLocation = startLocation;
    }

    public ObjectId getEndLocation() {
        return endLocation;
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

    public void addStop(ObjectId smallStop, ObjectId bigStop) {
        for (int i = 0; i < stops.size(); i++) {
            if (stops.get(i).getBigStop().equals(bigStop)) {
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

        private final String startLocation;
        private final String endLocation;
        private final List<SerializedStop> stops;

        public SerializedTripData(TripData tripData) {
            this.startLocation = tripData.startLocation.toHexString();
            this.endLocation = tripData.endLocation.toHexString();
            this.stops = new ArrayList<SerializedStop>();
            for (Stop stop : tripData.getStops()) {
                this.stops.add(new SerializedStop(stop));
            }

        }
    }

    private class SerializedTripDataExpanded implements SerializingInterface {

        private class SerializedStop implements SerializingInterface {
            private final BigStops bigStop;
            private final List<SmallStops> smallStops;

            public SerializedStop(Stop stop, BigStopHandler bigStopHandler, SmallStopHandler smallStopHandler) {

                this.bigStop = bigStopHandler.getById(stop.getBigStop());
                this.smallStops = new ArrayList<SmallStops>();
                for (ObjectId id : stop.getSmallStops()) {
                    SmallStops temp = smallStopHandler.getById(id);
                    // maybe change this to like correcting it or something in the db but if managed
                    // correctly it should never happen
                    if (temp == null)
                        continue;
                    smallStops.add(temp);
                }
            }
        }

        private final BigStops startLocation;
        private final BigStops endLocation;
        private final List<SerializedStop> stops;

        public SerializedTripDataExpanded(TripData tripData, BigStopHandler bigStopHandler,
                SmallStopHandler smallStopHandler) {
            this.startLocation = bigStopHandler.getById(tripData.startLocation);
            this.endLocation = bigStopHandler.getById(tripData.endLocation);
            this.stops = new ArrayList<SerializedStop>();
            for (Stop stop : tripData.getStops()) {
                this.stops.add(new SerializedStop(stop, bigStopHandler, smallStopHandler));
            }

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
        SerializedTripDataExpanded temp = new SerializedTripDataExpanded(this, bigStopHandler, smallStopHandler);
        return temp.toJSONString(bigStopHandler, smallStopHandler);
    }

}
