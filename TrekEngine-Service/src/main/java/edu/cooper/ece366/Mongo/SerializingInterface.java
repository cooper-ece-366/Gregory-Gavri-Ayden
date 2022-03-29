package edu.cooper.ece366.Mongo;

import com.google.gson.Gson;

public interface SerializingInterface {
    default String toJSONString(){
        return new Gson().toJson(this); 
    }
}
