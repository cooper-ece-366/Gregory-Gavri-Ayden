package edu.cooper.ece366.Mongo.Stops.BigStops;

import edu.cooper.ece366.Mongo.MongoHandler;
import edu.cooper.ece366.Mongo.Stops.StopHandler;

public class BigStopHandler extends StopHandler<BigStops> {

    public BigStopHandler(MongoHandler handler) {
        super(handler, "bigStops", BigStops.class);
    }

}

