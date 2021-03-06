// Written By Gregory Presser
package edu.cooper.ece366.Mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

public class MongoHandler {
    private final MongoClient client;
    private final MongoDatabase db;
    public MongoHandler(){
        this("TrekEngine");
    }

    // intializes the mongodb client and databse with the given name
    public MongoHandler(String name) {
        client = new MongoClient(new MongoClientURI(System.getenv("MONGO_URI")));
        CodecRegistry defaultCodecRegistry = MongoClientSettings.getDefaultCodecRegistry();
        CodecRegistry fromProvider = CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(defaultCodecRegistry, fromProvider);
        db = client.getDatabase(name).withCodecRegistry(pojoCodecRegistry);
    }

    public <T> MongoCollection<T> getCollection(String name, Class<T> clazz) {
        return db.getCollection(name,clazz);
    }

}
