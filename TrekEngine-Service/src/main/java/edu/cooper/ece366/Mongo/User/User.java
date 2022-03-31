package edu.cooper.ece366.Mongo.User;

import com.google.gson.Gson;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import edu.cooper.ece366.Mongo.IDInterface;
import edu.cooper.ece366.Mongo.SerializingInterface;

// user POJO that is seralizedable to JSON 
public class User implements SerializingInterface, IDInterface {
    @BsonProperty("_id") private final String userId;
    @BsonProperty("firstName") private final String firstName;
    @BsonProperty("lastName") private final String lastName;
    @BsonProperty("email") private final String email;

    @BsonCreator
    public User(
            @BsonProperty("_id") String userId,
            @BsonProperty("firstName") String firstName,
            @BsonProperty("lastName") String lastName,
            @BsonProperty("email") String email
    ) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    
    @BsonProperty("_id")
    public String getId() {
        return userId; 
    }
    public String getFirstName() {
        return firstName; 
    }
    public String getLastName() {
        return lastName; 
    }
    public String getEmail() {
        return email; 
    }
  
    public String toJSONString() {
        return new Gson().toJson(this);
    }

    // public String toString() {
    //     return toJSONString();
    // }
}
