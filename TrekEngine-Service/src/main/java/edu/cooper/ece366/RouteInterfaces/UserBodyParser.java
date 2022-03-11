package edu.cooper.ece366.RouteInterfaces;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.gson.JsonObject;

import edu.cooper.ece366.Mongo.User.User;
import edu.cooper.ece366.Mongo.User.UserHandler;
import static edu.cooper.ece366.Utils.BodyParser.parseBody;

import spark.Request;
import spark.Response;
import spark.Route;
import static spark.Spark.post; 

public class UserBodyParser {
    private static UserHandler userHandler; 

    public static void setUserHandler (UserHandler handler) {
        userHandler = handler;
    }
    public interface ExternalUserBodyParser{

        // this is the handler for auth post 
        String handle(Request req, Response res, JsonObject body,User user); 
    }

    // does a post request but first parses the body and into a jsonObject (body) and validates the user beforehand
    public static void authPost(String route, ExternalUserBodyParser handler) {
        post(route, (UserBodyParserRoute)(req,res,body,user) -> {
            return handler.handle(req,res,body,user); 
        });
    }

    // interface for the post request to parse the body and and validate user 
    private interface UserBodyParserRoute extends Route {

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




