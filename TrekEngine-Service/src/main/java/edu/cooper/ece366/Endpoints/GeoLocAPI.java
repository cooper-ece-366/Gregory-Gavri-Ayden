package edu.cooper.ece366.Endpoints;
import edu.cooper.ece366.Utils.GeoLocationHandler;
import static spark.Spark.*;

public class GeoLocAPI {
    public static void paths(GeoLocationHandler geoHandler) {
        path("/geo", () -> {
            get("/direction", (req, res) -> geoHandler.directions(req.queryParams("start"), req.queryParams("end")));
            get("/search", (req, res) -> geoHandler.search(req.queryParams("search")));
        });
    }
}
