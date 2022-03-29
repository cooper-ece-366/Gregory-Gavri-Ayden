package edu.cooper.ece366.Mongo.User;

import com.google.gson.Gson;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

// user POJO that is seralizedable to JSON 
public class User {
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

    public String getUserId() {
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

    public String toString() {
        return toJSONString();
    }
}
