package edu.cooper.ece366;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import edu.cooper.ece366.Mongo.MongoHandler;
import edu.cooper.ece366.Mongo.Stops.BigStops.BigStopHandler;
import edu.cooper.ece366.Mongo.Stops.BigStops.BigStops;
import edu.cooper.ece366.Mongo.Trips.Detail;
import edu.cooper.ece366.Mongo.Trips.Meta;
import edu.cooper.ece366.Mongo.Trips.Stop;
import edu.cooper.ece366.Mongo.Trips.Tag;
import edu.cooper.ece366.Mongo.Trips.Trip;
import edu.cooper.ece366.Mongo.Trips.TripData;
import edu.cooper.ece366.Utils.TripAI.TripGenerator;

public class TripGeneratorUnitTest {

    private static MongoHandler mongoHandler;
    private static BigStopHandler bigStopHandler;
    private static Trip trip; 

    @BeforeAll
    public static void setup() {
        mongoHandler = new MongoHandler();
        bigStopHandler = new BigStopHandler(mongoHandler);
        assert(bigStopHandler.getCount() > 1);
        ArrayList<BigStops> stops = bigStopHandler.getAll();
        
        trip = new Trip(
            new ObjectId(),
            new Meta("Test Trip 1","Description","gpress2222@gmail.com",false,new Date(),new Date()),
            new TripData(
                stops.get(0).getId(),
                stops.get(1).getId(),
                new ArrayList<Stop>(){{
                    add(new Stop(stops.get(0).getId(),new ArrayList<ObjectId>()));
                    add(new Stop(stops.get(1).getId(),new ArrayList<ObjectId>()));
                }}
            ),
            new Detail(new Date(), 10, new ArrayList<Tag>())
        );  
    }

    @Test
    public void testInit() throws IOException{
        TripGenerator tripGenerator = new TripGenerator(trip, bigStopHandler,6);
        assert (tripGenerator != null);

        //If this fails um there could be traffic
        //Better test might be needed 
        assert (tripGenerator.getMinTripLen().getDurtaionS() >= 40000 && tripGenerator.getMinTripLen().getDurtaionS() <= 50000);
    }
    
    
}
