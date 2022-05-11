// Written By Ayden Shankman
package edu.cooper.ece366.Endpoints;
import edu.cooper.ece366.RouteInterfaces.BodyParserRoute;
import edu.cooper.ece366.Utils.GeoLocation.GeoLocationHandler;

import static spark.Spark.*;

import java.io.IOException;

public class GeoLocAPI {
    public static void paths(GeoLocationHandler geoHandler) {
        path("/geo", () -> {
            // get("/direction", (req, res) -> geoHandler.directions(req.queryParams("start"), req.queryParams("end")));
            get("/search", (req, res) -> geoHandler.search(req.queryParams("address")));
            get("/nearby", (req, res) -> geoHandler.nearby(req.queryParams("location"), req.queryParams("type"), req.queryParams("radius")));
            post("/directions", (BodyParserRoute)(req,res,body)->{
                try {
                    return geoHandler.directions(body.get("stops").getAsJsonArray()).toJSONString(); 
                } catch (IOException e){
                    res.status(500); 
                    return e.getMessage();
                }
            }); 
        });
    }
}
