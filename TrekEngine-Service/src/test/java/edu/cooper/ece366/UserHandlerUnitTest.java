package edu.cooper.ece366; 

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.cooper.ece366.Mongo.MongoHandler;
import edu.cooper.ece366.Mongo.User.User;
import edu.cooper.ece366.Mongo.User.UserHandler;

public class UserHandlerUnitTest {

    private static MongoHandler mongoHandler;
    private UserHandler underTest; 

    @BeforeAll
    public static void setup(){
        mongoHandler = new MongoHandler("TrekEngine-Test");
    }

    @BeforeEach
    public void reset(){
        underTest = new UserHandler(mongoHandler); 
        underTest.flush(); 
    }

    @Test
    public void testInsertIfNExists(){

        // before insert 
        assert(underTest.getCount() == 0);

        String userId = "testUserId"; 
        String firstName = "testFirstName"; 
        String lastName = "testLastName"; 
        String email = "testEmail"; 
        User user = underTest.insertIfNExists(userId, firstName, lastName, email); 
        assert(user.getId().equals(userId)); 
        assert(user.getFirstName().equals(firstName)); 
        assert(user.getLastName().equals(lastName)); 
        assert(user.getEmail().equals(email)); 

        // after insert
        assert(underTest.getCount() == 1);

        // repeat insert
        user = underTest.insertIfNExists(userId, firstName, lastName, email); 
        assert(user.getId().equals(userId)); 
        assert(user.getFirstName().equals(firstName)); 
        assert(user.getLastName().equals(lastName)); 
        assert(user.getEmail().equals(email)); 

        // after repeat insert
        assert(underTest.getCount() == 1);
    }

    @Test
    public void testGetUserFromEmailDNE(){
        User user = underTest.getUserFromEmail("testEmail"); 
        assert(user == null); 
    }

    @Test
    public void testGetUserFromEmail(){

        String userId = "testUserId"; 
        String firstName = "testFirstName"; 
        String lastName = "testLastName"; 
        String email = "testEmail"; 

        underTest.insertIfNExists(userId, firstName, lastName, email); 
        User user = underTest.getUserFromEmail(email); 

        assert(user.getId().equals(userId));
        assert(user.getFirstName().equals(firstName));
        assert(user.getLastName().equals(lastName));
        assert(user.getEmail().equals(email));
        
    }

}
