package edu.cooper.ece366.RouteInterfaces;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.gson.JsonObject;

import edu.cooper.ece366.Mongo.User.User;
import edu.cooper.ece366.Mongo.User.UserHandler;
import edu.cooper.ece366.Utils.BodyParser;
import spark.Request;
import spark.Response;
import spark.Route;


public class UserBodyParser {
    private static UserHandler userHandler; 

    public static void setUserHandler (UserHandler handler) {
        userHandler = handler;
    }

    public interface UserBodyParserRoute extends Route {

        default Object handle(Request req, Response res) {
            JsonObject body = BodyParser.parseBody(req);
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
            } catch (GeneralSecurityException e) {
                res.status(400);
                return "Illegal Token"; 
            } catch (IOException e) {
                res.status(400);
                return e.getMessage(); 
            }
    
            return handle(req,res, body, user); 
        }
    
        String handle(Request req, Response res, JsonObject body, User id_token);
    }
}


