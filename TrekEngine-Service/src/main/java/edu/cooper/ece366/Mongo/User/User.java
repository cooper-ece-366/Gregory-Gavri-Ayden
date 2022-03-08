package edu.cooper.ece366.Mongo.User;

import com.google.gson.Gson;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

public class User {
    @BsonProperty("_id") public final String userId;
    @BsonProperty("firstName") public final String firstName;
    @BsonProperty("lastName") public final String lastName;
    @BsonProperty("email") public final String email;

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

    public String toJSONString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String toString() {
        return toJSONString();
    }
}
