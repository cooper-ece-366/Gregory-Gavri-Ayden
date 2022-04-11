package edu.cooper.ece366.Mongo.User;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;

import edu.cooper.ece366.Exceptions.IllegalTokenException;
import edu.cooper.ece366.Exceptions.InvalidTokenException;
import edu.cooper.ece366.Mongo.CollectionHandler;
import edu.cooper.ece366.Mongo.MongoHandler;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Collections;


// runs operations on the user collection in the mongoDB
public class UserHandler extends CollectionHandler<User>{

    public UserHandler(MongoHandler handler){
        super(handler,"users",User.class); 
    }
    // returns user if it doesn't exist
    public User insertIfNExists(String userId, String firstName, String lastName, String email) {
        return insertIfNExists(new User(
            userId,
            firstName,
            lastName,
            email
        )); 
    }

    public User insertIfNExists(User user){
        Bson update = new Document("$setOnInsert", user);
        collection.updateOne(Filters.eq("_id",user.getId()),update,new UpdateOptions().upsert(true));
        return getById(user.getId());
    }

    // returns user from userId
    @Override
    public User getById(String userId) {
        ArrayList<User> users = collection.find(Filters.eq("_id",userId)).into(new ArrayList<>());
        return users.size() > 0 ? users.get(0) : null;
    }

    // Doesn't allow to query by ObjectId because they are invalid 
    @Override
    public User getById(ObjectId invalidId){
        throw new IllegalArgumentException("ObjectId type is not valid for User");
    }

    @Override
    public void delete(String id){
        collection.deleteOne(Filters.eq("_id",id));
    }

    @Override
    public void delete(ObjectId objectId){
        throw new IllegalArgumentException("ObjectId type is not valid for User");
    }

    public User getUserFromEmail(String email){
        ArrayList<User> users =  rawQuery(Filters.eq("email",email)); 
        return users.size() > 0 ? users.get(0) : null;
    }

    public User verifyUser(String idTokenString) throws IllegalTokenException, InvalidTokenException {
        // verify token
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(System.getenv("GOOGLE_CLIENT_ID")))
                .build();
        GoogleIdToken idToken; 
        try{
            idToken = verifier.verify(idTokenString);
        } catch (Exception e){
            throw new IllegalTokenException();
        }
        if (idToken == null)
            throw new InvalidTokenException();
        GoogleIdToken.Payload payload = idToken.getPayload();

        String userId = payload.getSubject();

        // Get profile information from payload
        String email = payload.getEmail();
        String name = (String) payload.get("name");
        name = name.split(" ")[0];
        String familyName = (String) payload.get("family_name");
        return insertIfNExists(userId,name,familyName,email);  // return user from db and add if he exists
    }
}
