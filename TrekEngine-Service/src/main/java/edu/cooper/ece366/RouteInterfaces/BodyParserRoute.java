package edu.cooper.ece366.RouteInterfaces;

import spark.Request;
import spark.Response;
import spark.Route;
import com.google.gson.JsonObject;

import edu.cooper.ece366.Utils.BodyParser;

public interface BodyParserRoute extends Route {
    default Object handle(Request req, Response res) {
        JsonObject body = BodyParser.parseBody(req);
        if(body == null) {
            res.status(400); 
            return "Invalid JSON";
        }
        return handle(req,res, body); 
    }

    String handle(Request req, Response res, JsonObject body);
}