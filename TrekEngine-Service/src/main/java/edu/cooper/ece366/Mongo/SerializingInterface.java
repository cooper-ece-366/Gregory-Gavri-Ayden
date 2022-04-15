package edu.cooper.ece366.Mongo;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List; 

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import edu.cooper.ece366.Mongo.Stops.BigStops.BigStopHandler;
import edu.cooper.ece366.Mongo.Stops.SmallStops.SmallStopHandler;

import java.util.Collection;

public interface SerializingInterface {
    default String toJSONString(){
        JsonObject obj = new JsonObject(); 
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field: fields){
            if(field.getName().contains("this$")) continue;
            Class<?>[] interfaces = field.getType().getInterfaces(); 
            field.setAccessible(true);
            if (field.getType() == List.class){
                Type gType = field.getGenericType();
                if (gType instanceof ParameterizedType){
                    ParameterizedType pType = (ParameterizedType)gType;
                    Type type = pType.getActualTypeArguments()[0]; 
                    Class<?> t =  (Class<?>)type; 
                    Class<?>[] genericInterfaces = t.getInterfaces(); 
                    if(Arrays.stream(genericInterfaces).filter(inter -> inter == SerializingInterface.class).count() >= 1){
                        try {
                            JsonArray arr = new JsonArray();
                            int size = ((List)field.get(this)).size();
                            for(int i = 0; i<size; i++){
                                SerializingInterface tempObj = (SerializingInterface)(((List)field.get(this)).get(i)); 
                                String temp = tempObj.toJSONString(); 
                                arr.add(new Gson().fromJson(temp, JsonElement.class)); 
                            }
                            obj.add(field.getName(), arr); 
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        field.setAccessible(false);
                        continue; 
                    }

                }
            
            } else if(Arrays.stream(interfaces).filter(inter -> inter == SerializingInterface.class).count() >= 1){
                try {
                    String temp = ((SerializingInterface)(field.get(this))).toJSONString(); 
                    obj.add(field.getName(), new Gson().fromJson(temp, JsonObject.class));    
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                field.setAccessible(false);
                continue; 
            }
            try {
                String temp = new Gson().toJson(field.get(this));
                obj.add(field.getName(), new Gson().fromJson(temp, JsonElement.class));
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
            field.setAccessible(false);

        }
        return new Gson().toJson(obj); 
    }

    default String toJSONString(BigStopHandler bigStopHandler, SmallStopHandler smallStopHandler){
        JsonObject obj = new JsonObject(); 
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field: fields){
            if(field.getName().contains("this$")) continue;
            Class<?>[] interfaces = field.getType().getInterfaces(); 
            field.setAccessible(true);
            if (field.getType() == List.class){
                Type gType = field.getGenericType();
                if (gType instanceof ParameterizedType){
                    ParameterizedType pType = (ParameterizedType)gType;
                    Type type = pType.getActualTypeArguments()[0]; 
                    Class<?> t =  (Class<?>)type; 
                    Class<?>[] genericInterfaces = t.getInterfaces(); 
                    if(Arrays.stream(genericInterfaces).filter(inter -> inter == SerializingInterface.class).count() >= 1){
                        try {
                            JsonArray arr = new JsonArray();

                            int size = ((List)field.get(this)).size(); 

                            for(int i = 0; i<size; i++){
                                SerializingInterface tempObj = (SerializingInterface)(((List)field.get(this)).get(i)); 
                                String temp = tempObj.toJSONString(bigStopHandler,smallStopHandler); 
                                arr.add(new Gson().fromJson(temp, JsonElement.class)); 
                            }
                            obj.add(field.getName(), arr); 
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        field.setAccessible(false);
                        continue; 
                    }

                }
            
            } else if(Arrays.stream(interfaces).filter(inter -> inter == SerializingInterface.class).count() >= 1){
                try {
                    String temp = ((SerializingInterface)(field.get(this))).toJSONString(bigStopHandler,smallStopHandler); 
                    obj.add(field.getName(), new Gson().fromJson(temp, JsonObject.class));    
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                field.setAccessible(false);
                continue; 
            }
            try {
                String temp = new Gson().toJson(field.get(this));
                obj.add(field.getName(), new Gson().fromJson(temp, JsonElement.class));
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
            field.setAccessible(false);

        }
        return new Gson().toJson(obj); 
    }
}
