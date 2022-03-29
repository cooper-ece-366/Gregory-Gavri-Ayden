package edu.cooper.ece366.Mongo.Trips;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;


public class Meta {
    @BsonProperty("name") public final String name;
    @BsonProperty("description") public final String description;
    @BsonProperty("user") public final String user;
    @BsonProperty("private") public final Boolean isPrivate;
    @BsonProperty("created") public final String created;
    @BsonProperty("updated") public final String updated;

    @BsonCreator
    public Meta(
            @BsonProperty("name") String name,
            @BsonProperty("description") String description,
            @BsonProperty("user") String user,
            @BsonProperty("private") Boolean isPrivate,
            @BsonProperty("created") String created,
            @BsonProperty("updated") String updated){
        this.name = name;
        this.description = description;
        this.user = user;
        this.isPrivate = isPrivate;
        this.created = created;
        this.updated = updated;
    }
    public String getName(){
        return name; 

    }
    public String getDescription(){
        return description; 

    }
    public String getUser(){
        return user; 

    }
    public Boolean getIsPrivate(){
        return isPrivate; 

    }
    public String getCreated(){
        return created; 

    }
    public String getUpdated(){
        return updated; 

    }
    
    
    
    
}
