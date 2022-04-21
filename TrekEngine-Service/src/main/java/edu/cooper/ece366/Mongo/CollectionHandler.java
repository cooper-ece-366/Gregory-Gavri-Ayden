package edu.cooper.ece366.Mongo;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

public abstract class CollectionHandler<T extends IDInterface> {

    protected MongoCollection<T> collection;

    public CollectionHandler(MongoHandler handler, String name, Class<T> clazz) {
        collection = handler.getCollection(name, clazz); 
    }

    public T getById(String id){
        return getById(new ObjectId(id));
    }

    public T getById(ObjectId id){
        ArrayList<T> lst = collection.find(Filters.eq("_id",id)).into(new ArrayList<>());
        return lst.size() > 0 ? lst.get(0) : null;
    }

    public void insert(T obj) {
        collection.insertOne(obj);
    }

    public void insert(List<T> objs){
        collection.insertMany(objs);
    }

    public void delete(ObjectId objectId){
        collection.deleteOne(Filters.eq("_id",objectId));
    }

    public void delete(String objectId){
        delete(new ObjectId(objectId));
    }

    public void update(T obj){
        collection.updateOne(Filters.eq("_id",obj.getId()), new Document().append("$set", obj));
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

    public ArrayList<T> rawQuery (Bson filter){
        return collection.find(filter).into(new ArrayList<>());
    }

    public ArrayList<T> getAll (){
        return collection.find().into(new ArrayList<>());
    }
}
