package edu.cooper.ece366.Mongo.Stops.SmallStops;

import edu.cooper.ece366.Mongo.MongoHandler;
import edu.cooper.ece366.Mongo.Stops.StopHandler;

public class SmallStopHandler extends StopHandler<SmallStops> {


    public SmallStopHandler(MongoHandler handler) {
        super(handler, "smallStop", SmallStops.class);
    }

}
