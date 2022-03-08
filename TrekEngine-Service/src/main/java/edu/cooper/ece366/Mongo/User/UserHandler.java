package edu.cooper.ece366.Mongo.User;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import edu.cooper.ece366.Mongo.MongoHandler;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;

public class UserHandler {
    private MongoCollection<Document> collection;

    public UserHandler(MongoHandler handler){
        collection = handler.getCollection("users");
    }

    // returns user if it doesn't exist
    public User insertIfNExists(String userId, String firstName, String lastName, String email) {
        Bson update = new Document("$setOnInsert", new Document().append("_id", userId).append("firstName", firstName).append("lastName", lastName).append("email",email));
        UpdateResult result = collection.updateOne(Filters.eq("_id",userId),update,new UpdateOptions().upsert(true));
        return getUserFromID(userId);
    }

    // returns user from userId
    public User getUserFromID(String userId) {
        ArrayList<User> users = collection.find(Filters.eq("_id",userId), User.class).into(new ArrayList<>());
        return users.get(0);
    }

    public User getUserFromEmail(String email){
        ArrayList<User> users = collection.find(Filters.eq("email",email), User.class).into(new ArrayList<>());
        return users.get(0);
    }

    public User verifyUser(String idTokenString) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(System.getenv("GOOGLE_CLIENT_ID")))
                .build();
        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken == null)
            throw new GeneralSecurityException();
        GoogleIdToken.Payload payload = idToken.getPayload();
        // Print user identifier

        String userId = payload.getSubject();

        // Get profile information from payload
        String email = payload.getEmail();
        String name = (String) payload.get("name");
        name = name.split(" ")[0];
        String familyName = (String) payload.get("family_name");
        return insertIfNExists(userId,name,familyName,email);
    }
}
