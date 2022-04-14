package edu.cooper.ece366.Mongo.Stops.SmallStops;

import java.util.List;

import com.mongodb.client.model.Filters;

import org.bson.types.ObjectId;

import edu.cooper.ece366.Mongo.MongoHandler;
import edu.cooper.ece366.Mongo.Stops.StopHandler;

public class SmallStopHandler extends StopHandler<SmallStops> {


    public SmallStopHandler(MongoHandler handler) {
        super(handler, "smallStop", SmallStops.class);
    }

    public List<SmallStops> getStopsByBigStopType(ObjectId bigStopType) {
        return rawQuery(Filters.eq("bigStop", bigStopType));
    }

}
