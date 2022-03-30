package edu.cooper.ece366;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.cooper.ece366.Mongo.MongoHandler;
import edu.cooper.ece366.Mongo.Trips.Detail;
import edu.cooper.ece366.Mongo.Trips.Loacation;
import edu.cooper.ece366.Mongo.Trips.Meta;
import edu.cooper.ece366.Mongo.Trips.Tag;
import edu.cooper.ece366.Mongo.Trips.Trip;
import edu.cooper.ece366.Mongo.Trips.TripData;
import edu.cooper.ece366.Mongo.Trips.TripHandler;

public class TripHandlerUnitTest {
    private static MongoHandler mongoHandler;
    private TripHandler underTest; 

    @BeforeAll
    public static void setup(){
        mongoHandler = new MongoHandler("TrekEngine-Test");
    }

    @BeforeEach
    public void reset(){
        underTest = new TripHandler(mongoHandler); 
        underTest.flush(); 
    }

    @Test
    public void testInsert(){
        // insert trips into the databse
        Trip trip1 = new Trip(
            new ObjectId(),
            new Meta("Test Trip 1","Description","gpress2222@gmail.com",false,new Date(),new Date()),
            new TripData(new Loacation("start", 70.0,70.0,"city"),new Loacation("end", 80.0,80.0,"city"),new ArrayList<Loacation>()),
            new Detail(new Date(), 10, new ArrayList<Tag>())
        );  

        System.out.println(trip1.getMeta().getIsPrivate());
        // make sure there is a trip in the database 
        underTest.insert(trip1); 
        assert(underTest.getCount() == 1);

        // grab from the database and make sure they are equal
        assert(underTest.getById(trip1.getId())).equals(trip1); 
    }

    @Test
    public void testGetTripByTag(){

        // insert Trip

        Tag tag1 = new Tag("City", .5);
        Tag tag2 = new Tag("Park", 1.0); 
        Tag tag3 = new Tag("Hotel", .5); 
        Tag tag4 = new Tag("City",.3); 
        ArrayList<Tag> tags1 = new ArrayList<Tag>(); 
        tags1.add(tag1);
        tags1.add(tag2);
        tags1.add(tag3);

        ArrayList<Tag> tags2 = new ArrayList<Tag>(); 
        tags2.add(tag4);
        tags2.add(tag2);


        Trip trip1 = new Trip(
            new ObjectId(),
            new Meta("Test Trip 1","Description","gpress2222@gmail.com",false,new Date(),new Date()),
            new TripData(new Loacation("start", 70.0,70.0,"city"),new Loacation("end", 80.0,80.0,"city"),new ArrayList<Loacation>()),
            new Detail(new Date(), 10, tags1)
        ); 

        Trip trip2 = new Trip(
            new ObjectId(),
            new Meta("Test Trip 1","Description","gpress2222@gmail.com",false,new Date(),new Date()),
            new TripData(new Loacation("start", 70.0,70.0,"city"),new Loacation("end", 80.0,80.0,"city"),new ArrayList<Loacation>()),
            new Detail(new Date(), 10, tags2)
        );

        List<Trip> trips = new ArrayList<Trip>();
        trips.add(trip1);
        trips.add(trip2); 
        
        underTest.insert(trips);

        assert(underTest.getCount() == 2);

        // get trips by tag no weight 
        assert(underTest.getTripByTag(tag1.getTag()).size() == 2); 
        assert(underTest.getTripByTag(tag3.getTag()).size() == 1); 
        assert(underTest.getTripByTag("nothing").size() == 0); 


        // get trips by tag with weight
        assert(underTest.getTripByTag(tag1.getTag(), tag1.getWeight()).size() == 1);
        assert(underTest.getTripByTag(tag1.getTag(), tag4.getWeight()).size() == 2);
    }

    @Test
    public void testGetTripByUser(){
        Trip trip1 = new Trip(
            new ObjectId(),
            new Meta("Test Trip 1","Description","email1@gmail.com",false,new Date(),new Date()),
            new TripData(new Loacation("start", 70.0,70.0,"city"),new Loacation("end", 80.0,80.0,"city"),new ArrayList<Loacation>()),
            new Detail(new Date(), 10, new ArrayList<Tag>())
        ); 
        Trip trip2 = new Trip(
            new ObjectId(),
            new Meta("Test Trip 1","Description","email2@gmail.com",false,new Date(),new Date()),
            new TripData(new Loacation("start", 70.0,70.0,"city"),new Loacation("end", 80.0,80.0,"city"),new ArrayList<Loacation>()),
            new Detail(new Date(), 10, new ArrayList<Tag>())
        ); 
        Trip trip3 = new Trip(
            new ObjectId(),
            new Meta("Test Trip 1","Description","email2@gmail.com",false,new Date(),new Date()),
            new TripData(new Loacation("start", 70.0,70.0,"city"),new Loacation("end", 80.0,80.0,"city"),new ArrayList<Loacation>()),
            new Detail(new Date(), 10, new ArrayList<Tag>())
        ); 

        List<Trip> trips = new ArrayList<Trip>(){{
            add(trip1);
            add(trip2); 
            add(trip3); 
        }};
        underTest.insert(trips); 
        assert(underTest.getCount() == 3);

        assert(underTest.getTripByUser("email1@gmail.com").size() == 1);
        assert(underTest.getTripByUser("email2@gmail.com").size() == 2);
        assert(underTest.getTripByUser("email3@gmail.com").size() == 0);
        
    }

    @Test
    public void testGetTripByLength(){
        Trip trip1 = new Trip(
            new ObjectId(),
            new Meta("Test Trip 1","Description","email1@gmail.com",false,new Date(),new Date()),
            new TripData(new Loacation("start", 70.0,70.0,"city"),new Loacation("end", 80.0,80.0,"city"),new ArrayList<Loacation>()),
            new Detail(new Date(), 10, new ArrayList<Tag>())
        );
        Trip trip2 = new Trip(
            new ObjectId(),
            new Meta("Test Trip 1","Description","email1@gmail.com",false,new Date(),new Date()),
            new TripData(new Loacation("start", 70.0,70.0,"city"),new Loacation("end", 80.0,80.0,"city"),new ArrayList<Loacation>()),
            new Detail(new Date(), 20, new ArrayList<Tag>())
        );
        Trip trip3 = new Trip(
            new ObjectId(),
            new Meta("Test Trip 1","Description","email1@gmail.com",false,new Date(),new Date()),
            new TripData(new Loacation("start", 70.0,70.0,"city"),new Loacation("end", 80.0,80.0,"city"),new ArrayList<Loacation>()),
            new Detail(new Date(), 5, new ArrayList<Tag>())
        );
        Trip trip4 = new Trip(
            new ObjectId(),
            new Meta("Test Trip 1","Description","email1@gmail.com",false,new Date(),new Date()),
            new TripData(new Loacation("start", 70.0,70.0,"city"),new Loacation("end", 80.0,80.0,"city"),new ArrayList<Loacation>()),
            new Detail(new Date(), 10, new ArrayList<Tag>())
        );

        List<Trip> trips = new ArrayList<Trip>(){{
            add(trip1);
            add(trip2); 
            add(trip3); 
            add(trip4);
        }};
        underTest.insert(trips); 

        // eq to 
        assert(underTest.getCount() == 4);
        assert(underTest.getTripByLength(10,false,true).size() == 2);
        assert(underTest.getTripByLength(5,false,true).size() == 1);
        assert(underTest.getTripByLength(20,false,true).size() == 1);

        // gte and lte 
        assert(underTest.getTripByLength(5,true,false).size() == 4);
        assert(underTest.getTripByLength(5,false,false).size() == 1);
        assert(underTest.getTripByLength(10,true,false).size() == 3);
        assert(underTest.getTripByLength(10,false,false).size() == 3);
    }

    @Test
    public void testGetTripByLoc(){
        Trip trip1 = new Trip(
            new ObjectId(),
            new Meta("Test Trip 1","Description","email1@gmail.com",false,new Date(),new Date()),
            new TripData(new Loacation("New York", 70.0,70.0,"city"),new Loacation("LA", 80.0,80.0,"city"),new ArrayList<Loacation>()),
            new Detail(new Date(), 10, new ArrayList<Tag>())
        );
        Trip trip2 = new Trip(
            new ObjectId(),
            new Meta("Test Trip 1","Description","email1@gmail.com",false,new Date(),new Date()),
            new TripData(new Loacation("New York", 75.0,65.0,"city"),new Loacation("Chicago", 80.0,80.0,"city"),new ArrayList<Loacation>()),
            new Detail(new Date(), 10, new ArrayList<Tag>())
        );
        Trip trip3 = new Trip(
            new ObjectId(),
            new Meta("Test Trip 1","Description","email1@gmail.com",false,new Date(),new Date()),
            new TripData(new Loacation("Chicago", 80.0,70.0,"city"),new Loacation("LA", 80.0,80.0,"city"),new ArrayList<Loacation>()),
            new Detail(new Date(), 10, new ArrayList<Tag>())
        );

        List<Trip> trips = new ArrayList<Trip>(){{
            add(trip1);
            add(trip2); 
            add(trip3); 
        }};
        underTest.insert(trips); 
        assert(underTest.getCount() == 3);
        
        // start location
        assert(underTest.getTripByStartLoc("New York").size() == 2);
        assert(underTest.getTripByStartLoc("Chicago").size() == 1);
        assert(underTest.getTripByStartLoc("LA").size() == 0);

        // end location
        assert(underTest.getTripByEndLoc("New York").size() == 0);
        assert(underTest.getTripByEndLoc("Chicago").size() == 1);
        assert(underTest.getTripByEndLoc("LA").size() == 2);

        // start location by lng lat
        assert(underTest.getTripByStartLoc(-90.0,90.0,60.0,79.0).size() == 2);

    }
        
}
