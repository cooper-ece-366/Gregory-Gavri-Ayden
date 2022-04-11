package edu.cooper.ece366.Mongo.Trips;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonObject;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import edu.cooper.ece366.Mongo.SerializingInterface;

public class Detail implements SerializingInterface {
    @BsonProperty("startDate") private Date startDate;
    @BsonProperty("tripLength") private Integer tripLength;
    @BsonProperty("tags") private final List<Tag> tags;
    
    @BsonCreator
    public Detail(
        @BsonProperty("startDate") Date startDate,
        @BsonProperty("tripLength") Integer tripLength, 
        @BsonProperty("tags") List<Tag> tags) {
        this.startDate = startDate;
        this.tripLength = tripLength;
        this.tags = tags;
    }
    public Detail(JsonObject detailObj){
        this.startDate = new Date (detailObj.get("startDate").getAsLong());
        this.tripLength = detailObj.get("tripLength").getAsInt();
        this.tags = new ArrayList<Tag> (); 
        detailObj.get("tags").getAsJsonArray().forEach(tag -> {
            this.tags.add(new Tag(tag.getAsJsonObject()));
        });
    }
    public Date getStartDate(){
        return startDate; 
    }
    public void setStartDate(Date startDate){
        this.startDate = startDate; 
    }
    public Integer getTripLength(){
        return tripLength; 
    }
    public void setTripLength(Integer tripLength){
        this.tripLength = tripLength; 
    }
    public List<Tag> getTags(){
        return tags; 
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Detail)) {
            return false;
        }
        Detail detail = (Detail) o;
        return startDate.equals(detail.startDate) &&
               tripLength.equals(detail.tripLength) &&
               tags.equals(detail.tags);
    }

    
}
