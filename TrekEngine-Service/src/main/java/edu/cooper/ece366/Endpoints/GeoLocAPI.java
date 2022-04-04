package edu.cooper.ece366.Endpoints;
import edu.cooper.ece366.RouteInterfaces.BodyParserRoute;
import edu.cooper.ece366.Utils.GeoLocationHandler;
import static spark.Spark.*;

public class GeoLocAPI {
    public static void paths(GeoLocationHandler geoHandler) {
        // login post request authenticator and returns a user object to the client
        path("/geo", () -> {
            // get("/direction", (req, res) -> geoHandler.directions(req.queryParams("start"), req.queryParams("end")));
            get("/search", (req, res) -> geoHandler.search(req.queryParams("address")));
            get("/nearby", (req, res) -> geoHandler.nearby(req.queryParams("location"), req.queryParams("type"), req.queryParams("radius")));
            post("/directions", (BodyParserRoute)(req,res,body)->{
                try {
                    return geoHandler.directions(body.get("stops").getAsJsonArray()); 
                } catch (Exception e){
                    res.status(500); 
                    return e.getMessage();
                }
            }); 
        });
    }
}
