package edu.cooper.ece366.Utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import spark.Request;


public class BodyParser {
    // parses JSON to a jsonObject 
    public static JsonObject parseJSON(String obj_string){
        if (obj_string == null || obj_string.isEmpty()) 
            return null;
        JsonObject json = new Gson().fromJson(obj_string, JsonObject.class);
        if (json == null) 
            return null;
        return json;
    }
    // parses the body of a request to JSON 
    public static JsonObject parseBody(Request req) {
        return parseJSON(req.body());
    } 
    
}
