// Written By Gregory Presser
package edu.cooper.ece366.RouteInterfaces;

import com.google.gson.JsonObject;

import edu.cooper.ece366.Mongo.User.User;
import edu.cooper.ece366.Mongo.User.UserHandler;
import static edu.cooper.ece366.Utils.BodyParser.parseBody;

import spark.Request;
import spark.Response;
import spark.Route;

public class UserBodyParser {
    private static UserHandler userHandler; 

    public static void setUserHandler (UserHandler handler) {
        userHandler = handler;
    }
    // interface for the post request to parse the body and and validate user 
    public interface AuthRoute extends Route {

        default Object handle(Request req, Response res) {
            JsonObject body = parseBody(req);
            if(body == null) {
                res.status(400); 
                return "Invalid JSON";
            } 
            if(body.get("id_token") == null) {
                res.status(400);
                return "Missing id_token";
            }
            String id_token = body.get("id_token").getAsString();
            User user; 
            try {
                user = userHandler.verifyUser(id_token);
            } catch (Exception e) {
                res.status(400);
                return e.getMessage(); 
            }
    
            return handle(req,res, body, user); 
        }
    
        String handle(Request req, Response res, JsonObject body, User id_token);
    }

    // interface for the post request to parse the body and and validate user but will still allow non-authenticated users to post
    public interface QAuthRoute extends Route {

        default Object handle(Request req, Response res) {
            JsonObject body = parseBody(req);
            if(body == null) {
                res.status(400); 
                return "Invalid JSON";
            } 
            if(body.get("id_token") == null) {
                return handle(req,res, body, null); 
            }
            String id_token = body.get("id_token").getAsString();
            User user; 
            try {
                user = userHandler.verifyUser(id_token);
            } catch (Exception e) {
                return handle(req,res, body, null);  
            }
            return handle(req,res, body, user); 
        }
        String handle(Request req, Response res, JsonObject body, User id_token);
    }
}




