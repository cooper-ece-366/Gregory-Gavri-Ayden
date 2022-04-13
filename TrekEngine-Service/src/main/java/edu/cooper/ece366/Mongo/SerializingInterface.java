package edu.cooper.ece366.Mongo;

import java.lang.reflect.*;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public interface SerializingInterface {
    default String toJSONString(){
        JsonObject obj = new JsonObject(); 
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field: fields){
            Class<?>[] interfaces = field.getType().getInterfaces(); 
            if(Arrays.stream(interfaces).filter(inter -> inter == SerializingInterface.class).count() >= 1){
                try {
                   obj.add(field.getName(), new Gson().fromJson(((SerializingInterface)(field.get(this))).toJSONString(), JsonObject.class)) ;    
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                obj.add(field.getName(), new Gson().fromJson(field.get(this), JsonObject.class));

            }
        }
        return new Gson().toJson(this); 
    }
}
