package edu.cooper.ece366;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.cooper.ece366.Mongo.CollectionHandler;
import edu.cooper.ece366.Mongo.MongoHandler;
import com.mongodb.client.model.Filters;

public class CollectionHandlerUnitTest {

    private static MongoHandler mongoHandler;
    private CollectionHandler underTest;


    @BeforeAll
    public static void setup() {
        mongoHandler = new MongoHandler("TrekEngine-Test");
    }


    @BeforeEach
    public void reset() {
        underTest = new CollectionHandler(mongoHandler, "testCollection");
    }

    @Test
    public void testGetCountNoFilter() {
        // given result is 2 in db 
        assert(underTest.getCount() == 2);
    }

    @Test
    public void testGetCountFilter() {
        // given result is 1 in db 
        assert(underTest.getCount(Filters.eq("name", "name1")) == 1);
    }

}
