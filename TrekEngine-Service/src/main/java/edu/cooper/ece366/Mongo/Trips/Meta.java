// Written By Gregory Presser
package edu.cooper.ece366.Mongo.Trips;

import java.util.Date;

import com.google.gson.JsonObject;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import edu.cooper.ece366.Mongo.SerializingInterface;
import edu.cooper.ece366.Mongo.User.User;
import edu.cooper.ece366.Mongo.User.UserHandler;


public class Meta implements SerializingInterface{
    @BsonProperty("name") private String name;
    @BsonProperty("description") private String description;
    @BsonProperty("user") private final String user;
    @BsonProperty("private") private Boolean isPrivate;
    @BsonProperty("created") private final Date created;
    @BsonProperty("updated") private Date updated;


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

    public Meta(JsonObject metaJson){
        this.name = metaJson.get("name").getAsString();
        this.description = metaJson.get("description").getAsString();
        this.user = metaJson.get("user").getAsString();
        if (metaJson.has("private"))
            this.isPrivate = metaJson.get("private").getAsBoolean();
        else
            this.isPrivate = metaJson.get("isPrivate").getAsBoolean();
        this.created = new Date (metaJson.get("created").getAsLong()); 
        this.updated = new Date (metaJson.get("updated").getAsLong()); 
    }

    public Meta(Meta meta){
        this.name = meta.name;
        this.description = meta.description;
        this.user = meta.user;
        this.isPrivate = meta.isPrivate;
        this.created = meta.created;
        this.updated = meta.updated;
    }


    public User getUserObj(UserHandler userHandler){
        return userHandler.getUserFromEmail(user);
    }

    public String getName(){
        return name; 
    }

    public void setName(String name){
        this.name = name; 
    }
    public String getDescription(){
        return description; 
    }
    public void setDescription(String description){
        this.description = description; 
    }
    public String getUser(){
        return user; 
    }
    public Boolean getIsPrivate(){
        return isPrivate; 
    }
    public void setIsPrivate(Boolean isPrivate){
        this.isPrivate = isPrivate; 
    }
    public Date getCreated(){
        return created; 
    }
    public Date getUpdated(){
        return updated; 
    }
    public void setUpdated(Date updated){
        this.updated = updated; 
    }

    @Override
    public boolean equals(Object o){
        if (o == this) return true;
        if (!(o instanceof Meta)) {
            return false;
        }
        Meta other = (Meta) o;
        return this.name.equals(other.name) && this.description.equals(other.description) && this.user.equals(other.user) && this.isPrivate.equals(other.isPrivate) && this.created.equals(other.created) && this.updated.equals(other.updated);
    }
}
