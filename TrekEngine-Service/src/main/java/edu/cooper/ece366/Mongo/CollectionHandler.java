package edu.cooper.ece366.Mongo;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;

public class CollectionHandler {

    protected MongoCollection<Document> collection;

    public CollectionHandler(MongoHandler handler, String name) {
        collection = handler.getCollection(name);
    }

    public void flush(){
        collection.drop();
    }

    public long getCount(){
        return collection.countDocuments();
    }

    public long getCount(Bson filter){
        return collection.countDocuments(filter);
    }
}
