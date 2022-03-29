package edu.cooper.ece366.Mongo.Trips;

import java.util.Date;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;


public class Meta {
    @BsonProperty("name") private final String name;
    @BsonProperty("description") private final String description;
    @BsonProperty("user") private final String user;
    @BsonProperty("private") private final Boolean isPrivate;
    @BsonProperty("created") private final Date created;
    @BsonProperty("updated") private final Date updated;



    @BsonCreator
    public Meta(
            @BsonProperty("name") String name,
            @BsonProperty("description") String description,
            @BsonProperty("user") String user,
            @BsonProperty("private") Boolean isPrivate,
            @BsonProperty("created") Date created,
            @BsonProperty("updated") Date updated){
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
    public Date getCreated(){
        return created; 
    }
    public Date getUpdated(){
        return updated; 
    }
}
