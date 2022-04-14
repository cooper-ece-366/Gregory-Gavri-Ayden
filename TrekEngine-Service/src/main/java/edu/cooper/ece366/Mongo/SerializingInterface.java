package edu.cooper.ece366.Mongo;

import java.lang.reflect.*;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import edu.cooper.ece366.Mongo.Stops.BigStops.BigStopHandler;
import edu.cooper.ece366.Mongo.Stops.SmallStops.SmallStopHandler;

public interface SerializingInterface {
    default String toJSONString(){
        JsonObject obj = new JsonObject(); 
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field: fields){
            if(field.getName() == "this$0") continue;
            Class<?>[] interfaces = field.getType().getInterfaces(); 
            field.setAccessible(true);
            if(Arrays.stream(interfaces).filter(inter -> inter == SerializingInterface.class).count() >= 1){
                System.out.println("SerializingInterface");
                try {
                    String temp = ((SerializingInterface)(field.get(this))).toJSONString(); 
                    obj.add(field.getName(), new Gson().fromJson(temp, JsonObject.class));    
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("not SerializingInterface");
                try {
                    String temp = new Gson().toJson(field.get(this));
                    obj.add(field.getName(), new Gson().fromJson(temp, JsonElement.class));
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            field.setAccessible(false);

        }
        return new Gson().toJson(obj); 
    }

    default String toJSONString(BigStopHandler bigStopHandler, SmallStopHandler smallStopHandler){
        JsonObject obj = new JsonObject(); 
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field: fields){
            if(field.getName() == "this$0") continue;
            Class<?>[] interfaces = field.getType().getInterfaces(); 
            field.setAccessible(true);
            if(Arrays.stream(interfaces).filter(inter -> inter == SerializingInterface.class).count() >= 1){
                System.out.println("SerializingInterface");
                try {
                    String temp = ((SerializingInterface)(field.get(this))).toJSONString(bigStopHandler,smallStopHandler); 
                    obj.add(field.getName(), new Gson().fromJson(temp, JsonObject.class));    
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("not SerializingInterface");
                try {
                    String temp = new Gson().toJson(field.get(this));
                    obj.add(field.getName(), new Gson().fromJson(temp, JsonElement.class));
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            field.setAccessible(false);

        }
        return new Gson().toJson(obj);  
    }
}
