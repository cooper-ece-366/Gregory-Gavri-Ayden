package edu.cooper.ece366;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.cooper.ece366.Mongo.MongoHandler;
import edu.cooper.ece366.Mongo.Stops.BigStops.BigStopHandler;
import edu.cooper.ece366.Mongo.Stops.BigStops.BigStops;

public class BigStopHandlerUnitTest {
    private static MongoHandler mongoHandler;
    private BigStopHandler underTest; 

    @BeforeAll
    public static void setup(){
        mongoHandler = new MongoHandler("TrekEngine-Test");
    }

    @BeforeEach
    public void reset(){
        underTest = new BigStopHandler(mongoHandler);
        underTest.flush();
    }

    @Test
    public void testInsert(){
        ObjectId id = new ObjectId(); 
        BigStops bigStop = new BigStops(id, "name1", 0.0, 0.0, "type1",true);
        underTest.insert(bigStop);
        assert(underTest.getCount() == 1);
        BigStops test = underTest.getById(id);  
        assert(test.equals(bigStop)); 

    }
    @Test
    public void testGetStopsByType(){
        ArrayList<BigStops> bigStops = new ArrayList<BigStops>(){{
            add(new BigStops(new ObjectId(), "name1", 0.0, 0.0, "type1",true));
            add(new BigStops(new ObjectId(), "name2", 0.0, 0.0, "type2",true));
            add(new BigStops(new ObjectId(), "name1", 0.0, 0.0, "type1",true));
            add(new BigStops(new ObjectId(), "name2", 0.0, 0.0, "type2",true));
        }}; 
        underTest.insert(bigStops); 
        assert(underTest.getCount() == 4); 
        assert(underTest.getStopsByType("type1").size() == 2);
        assert(underTest.getStopsByType("type2").size() == 2);
    }
    // @Test
    // public void testGetStopsByLoc(){ 
    //     BigStops stop1 = new BigStops(new ObjectId(),"New York", 70.0,70.0,"city",true); 
        
    //     BigStops stop2 = new BigStops( new ObjectId(),"New York", 75.0,65.0,"city",true); 
            
    //     BigStops stop3 = new BigStops(new ObjectId(),"Chicago", 80.0,70.0,"city",true); 

    //     List<BigStops> stops = new ArrayList<BigStops>(){{
    //         add(stop1);
    //         add(stop2); 
    //         add(stop3); 
    //     }};
    //     underTest.insert(stops); 
    //     assert(underTest.getCount() == 3);
        
    //     // start location by lng lat
    //     // assert(underTest.getStopsByLoc(-90,-90,90,79).size() == 2);

    // }
    @Test
    public void testGetStopsByName(){
        BigStops stop1 = new BigStops(new ObjectId(),"New York", 70.0,70.0,"city",true); 
        
        BigStops stop2 = new BigStops( new ObjectId(),"New York", 75.0,65.0,"city",true); 
            
        BigStops stop3 = new BigStops(new ObjectId(),"Chicago", 80.0,70.0,"city",true); 

        List<BigStops> stops = new ArrayList<BigStops>(){{
            add(stop1);
            add(stop2); 
            add(stop3); 
        }};
        underTest.insert(stops); 
        assert(underTest.getCount() == 3);
        assert(underTest.getStopsByName("New York").size() == 2);
        assert(underTest.getStopsByName("Chicago").size() == 1);

    }

    @Test
    public void testCurated(){
        BigStops stop1 = new BigStops(new ObjectId(),"New York", 70.0,70.0,"city",true); 
        
        BigStops stop2 = new BigStops( new ObjectId(),"New York", 75.0,65.0,"city",false); 

        List<BigStops> stops = new ArrayList<BigStops>(){{
            add(stop1);
            add(stop2); 
        }};

        underTest.insert(stops);
        assert(underTest.getCuratedStopsByName("New York").size() == 1);
        assert(underTest.getStopsByName("New York").size() == 2);
        
    }
}
